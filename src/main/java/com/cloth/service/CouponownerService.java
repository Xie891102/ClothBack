package com.cloth.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloth.Repository.CouponRepository;
import com.cloth.Repository.CouponownerRepository;
import com.cloth.model.Coupon;
import com.cloth.model.Couponowner;
import com.cloth.model.Users;


@Service
public class CouponownerService {

	@Autowired
	private CouponownerRepository couponownerRepository;
	
	@Autowired
    private CouponRepository couponRepository; 
	
	  @Transactional
	    public boolean claimCoupon(Integer usersId, Integer couponId) {
	        // 檢查該用戶是否已經領取過該折價券
	        boolean alreadyClaimed = couponownerRepository.existsByUsersIdAndCouponId(usersId, couponId);
	        if (alreadyClaimed) {
	            return false; // 已經領取過
	        }

	        // 獲取 Coupon 信息
	        Coupon coupon = couponRepository.findById(couponId).orElse(null);
	        if (coupon == null) {
	            return false; // 折價券不存在
	        }

	        // 創建並保存 Couponowner 實例
	        Couponowner couponowner = new Couponowner();
	        Users user = new Users(); // 創建 Users 對象
	        user.setId(usersId); // 設置用戶 ID
	        couponowner.setUsers(user);// 創建用戶對象，實際應該從用戶服務中獲取
	        couponowner.setCoupon(coupon);
	        couponowner.setStatus(1); // 設置狀態為已領取
	        couponowner.setStart_at(new Date());
	        couponowner.setEnd_at(getCouponEndDate(coupon.getEnd_at()));

	        couponownerRepository.save(couponowner);
	        return true;
	    }

	    private Date getCouponEndDate(Date couponEndDate) {
	        // 設置有效期為折價券原有的 end_at
	        return couponEndDate;
	    }
    
    @Transactional // Ensure this is present
    public void updateCouponStatusToZero(List<Integer> ids) {
        try {
            ids.forEach(id -> couponownerRepository.updateCouponStatusToZero(id));
        } catch (Exception e) {
            // Error handling
            e.printStackTrace();
            throw new RuntimeException("Error updating coupon status", e);
        }
    }
 
}
