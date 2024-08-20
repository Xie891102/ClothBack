package com.cloth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloth.Repository.UsersRepository;
import com.cloth.model.Users;

import io.jsonwebtoken.io.IOException;

@Service
@Transactional
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder; // 增加密碼編碼器的注入

// 登入=====================================================================================================
	public Users login(String email, String password) {
		Optional<Users> optional = usersRepository.findByEmail(email);
		if (optional.isPresent()) {
			Users user = optional.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return user;
			}
		}
		return null;
	}
	
// 找尋ID===============================================================================================
	public Users findById(Integer id) {
        if (id != null) {
            Optional<Users> optional = usersRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
	

// 更改密碼=====================================================================================================
	public boolean changePassword(String email, String oldPassword, String newPassword) {
		Optional<Users> optional = usersRepository.findByEmail(email);
		if (optional.isPresent()) {
			Users user = optional.get();

			// 檢查舊密碼是否正確
			if (passwordEncoder.matches(oldPassword, user.getPassword())) {
				// 將新密碼進行加密
				String encryptedPassword = passwordEncoder.encode(newPassword);

				// 設置加密後的密碼並儲存
				user.setPassword(encryptedPassword);
				usersRepository.save(user); // 保存更新後的實體
				return true;
			}
		}
		return false;
	}

// 儲存密碼=====================================================================================================
	public Users save(Users user) {
		// 在保存前對密碼進行加密
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usersRepository.save(user);
	}

// 查詢email=============================================================================================
	public Users findByEmail(String email) {
		Optional<Users> optional = usersRepository.findByEmail(email);
		return optional.orElse(null);
	}

// 查詢userName=============================================================================================
	public Users findByUsername(String username) {
		Optional<Users> optional = usersRepository.findByUsername(username);
		return optional.orElse(null);

	}

// 使用者是否存在============================================================================================
	public boolean userExists(String email) {
		return usersRepository.findByEmail(email).isPresent();
	}

// 註冊============================================================================================
	public void registerUser(String email, String username, String password, String gender, String birth, String phone,
	        String address, byte[] filess) {
	    Users user = new Users();
	    user.setEmail(email);       // 設置電子郵件
	    user.setUsername(username); // 設置用戶名
	    user.setPassword(password); // 設置密碼
	    user.setGender(gender);     // 設置性別
	    user.setBirth(birth);       // 設置生日
	    user.setPhone(phone);       // 設置電話
	    user.setAddress(address);   // 設置地址
	    user.setImg(filess);        // 設置照片
	    usersRepository.save(user); // 儲存使用者到數據庫
	}


//編輯會員個人資料 ===========================================================================
	public Users modifyByEmail(String email, String username, String password, String gender, String birth, String phone, String address, MultipartFile avatarFile) {
	    Users user = usersRepository.findByEmail(email).orElse(null);
	    if (user != null) {
	        if (username != null)
	            user.setUsername(username);
	        if (password != null && !password.isEmpty())
	            user.setPassword(passwordEncoder.encode(password));
	        if (gender != null)
	            user.setGender(gender);
	        if (birth != null)
	            user.setBirth(birth);
	        if (phone != null)
	            user.setPhone(phone);
	        if (address != null)
	            user.setAddress(address);
	        if (avatarFile != null && !avatarFile.isEmpty()) {
	            try {
	                try {
						user.setImg(avatarFile.getBytes());
					} catch (java.io.IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        usersRepository.save(user);
	    }
	    return user;
	}


}