package com.cloth.Controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloth.model.Users;
import com.cloth.service.EmailService;
import com.cloth.service.UsersService;
import com.cloth.util.JsonWebTokenUtility;
import com.cloth.util.VerificationCodeGenerator;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UsersController {

    @Autowired     //必要工具
    private UsersService usersService;

    @Autowired    // 注入傳送驗證碼
    private EmailService emailService;               
    
    @Autowired    // 注入JWT工具類
    private JsonWebTokenUtility jsonWebTokenUtility;  
    
    @Autowired    // 注入密碼加密
    private PasswordEncoder passwordEncoder;          
    
    
// 登入‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
    @PostMapping("/login")
    public String login(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        String email = obj.getString("email");
        String password = obj.getString("password");
        
        // 檢查用戶名和密碼是否為空
        if (email.isEmpty() || password.isEmpty()) {
            responseBody.put("success", false);
            responseBody.put("message", "請輸入帳號和密碼");
            return responseBody.toString();
        }

        // 查找用戶
        Users user = usersService.findByEmail(email);
        if (user == null) {
            // 驗證用戶是否存在
            responseBody.put("success", false);
            responseBody.put("message", "帳號未註冊");
            return responseBody.toString();
        }

        // 取出資料庫密碼(加密過)
        String encryptedPassword = user.getPassword();

        // 驗證密碼
        boolean passwordMatches = passwordEncoder.matches(password, encryptedPassword);
        if (!passwordMatches) {
            responseBody.put("success", false);
            responseBody.put("message", "帳號或密碼錯誤");
            return responseBody.toString();
        }

        // 登入成功，生成JWT
        String token = jsonWebTokenUtility.createToken(email, null);

        // 返回成功訊息和JWT給客戶端
        responseBody.put("success", true);
        responseBody.put("message", "登錄成功");
        responseBody.put("token", token);
        responseBody.put("userEmail", user.getEmail());
        responseBody.put("userId", user.getId());
        responseBody.put("username", user.getUsername()); // 添加用戶名

        return responseBody.toString();
    }


// 顯示用戶大頭照‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
    private byte[] img = null;
    @GetMapping(
    	    path = "/detail/{email}",
    	    produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    	)
    	public ResponseEntity<byte[]> findPhotoByPhotoId(@PathVariable(name = "email") String email) {
    	    Users detail = usersService.findByEmail(email);
    	    byte[] result = (detail != null && detail.getImg() != null) ? detail.getImg() : this.img;

    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setContentType(MediaType.IMAGE_JPEG); // 可以根據實際情況設置為 PNG 或其他格式

    	    return new ResponseEntity<>(result, headers, HttpStatus.OK);
    	}

  
// 傳送驗證碼到顧客email ‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
    public String sendEmail(@RequestParam String to, @RequestParam String subject,
    		                @RequestParam String text) {
        emailService.sendEmail(to, subject, text);
        return "Email sent successfully✅";
    }
  
    
// ‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵註冊開始‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵  
// 註冊分為四步-> 1.使用者輸入email，系統發送驗證碼  2.使用者輸入驗證碼  3.註冊第一部分(用戶名、密碼、性別)  4.註冊第二部分(生日、手機號碼、地址、大頭照)
// 為了做分頁會將註冊前一部分儲存於變數中，到下一步一起送到資料庫
    
// 暫時使用變數儲存 (不是好作法，理論上要使用session，這樣多用戶同時請求時，會爆掉)
    String verificationCode = null;  // 驗證碼
    String email = null;             // 使用者的電子郵件
    String username = null;          // 使用者的用戶名
    String password = null;          // 使用者的密碼
    String gender = null;            // 使用者的性別
    String birth = null;             // 使用者的生日
    String phone = null;             // 使用者的電話號碼
    String address = null;           // 使用者的地址
    String encryptedPassword = null; // 加密過的密碼
    MultipartFile filess = null;     // 存儲使用者上傳的大頭照（以 byte[] 形式）

    
// 註冊 第一步:檢查信箱、發送驗證碼==========================================================================  
    @PostMapping("/register/step1") 
    public String registerStep1(@RequestBody String body, HttpSession session) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        email = obj.getString("email");   // 從前端獲取使用者輸入的電子郵件

        // 檢查用戶是否已經存在
        if (usersService.userExists(email)) {
            responseBody.put("success", false);
            responseBody.put("message", "帳號已存在");
            return responseBody.toString();
        }

        // 生成驗證碼並發送郵件
        verificationCode = VerificationCodeGenerator.generateVerificationCode();
        emailService.sendEmail(email, "驗證您的帳號", "您的驗證碼是: " + verificationCode);
   
        // 檢查程式傳送的值 =================================================================================
        System.out.println("註冊 第一步: 檢查電子信箱、發送驗證碼==================================");
        System.out.println("=> 電子信箱= "+email);
        System.out.println("=> 系統生成的驗證碼= "+verificationCode); 
        
        // 將 email 和驗證碼存入 session
        session.setAttribute("email", email);

        responseBody.put("success", true);
        responseBody.put("message", "驗證碼已發送到您的郵箱");

        return responseBody.toString();
    }

  
// 註冊 第二步:驗證驗證碼 =========================================================================   
    @PostMapping("/register/step2")
    public String registerStep2(@RequestBody String body, HttpSession session) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        
        String savedVerificationCode = verificationCode;                       // 從變數中獲取已儲存的驗證碼
        String inputVerificationCode = obj.getString("inputVerificationCode"); // 從前端傳入的驗證碼
        
        System.out.println("註冊 第二步: 驗證驗證碼=========================================");
        System.out.println("=> 電子信箱= "+email);
        System.out.println("=> 系統生成的驗證碼= "+verificationCode);
        System.out.println("=> 儲存的驗證碼= "+savedVerificationCode);
        System.out.println("=> 使用者輸入的驗證碼= "+inputVerificationCode);

        // 將使用者輸入的驗證碼與 session 中的驗證碼比對
        if (!inputVerificationCode.equals(savedVerificationCode)) {
            responseBody.put("success", false);
            responseBody.put("message", "驗證碼錯誤");
            return responseBody.toString();
        }

        // 清除session中的驗證碼以防重複使用
        session.removeAttribute("verificationCode");

        responseBody.put("success", true);
        responseBody.put("message", "驗證碼驗證成功，資料已儲存");

        return responseBody.toString();
    }

    
 // 註冊 第三步:填寫個人資料 =====================================================================
    @PostMapping("/register/step3")
    public String registerStep3(
        @RequestParam("username") String usernamee,     // 使用usernamee 變數儲存
        @RequestParam("password") String passwordd,     // 使用passwordd 變數儲存
        @RequestParam("email") String email
    ) {

        JSONObject responseBody = new JSONObject();

        System.out.println("註冊 第三步: 註冊 用戶名、密碼 ===================================");
        System.out.println("  變數儲存用戶名: " + usernamee);    
        System.out.println("  變數儲存密碼(未加密): " + passwordd);          
        System.out.println("  電子信箱: " + email);            

        username = usernamee;    // 使用usernamee 變數儲存      
        password = passwordEncoder.encode(passwordd);    // 將密碼進行加密，並且存入變數 password 中
        System.out.println("系統加密過的密碼= "+encryptedPassword);

        responseBody.put("success", true);
        responseBody.put("message", "信箱、用戶名、密碼、性別已儲存到資料庫");
        return responseBody.toString();
    }

// 註冊 第四步:填寫個人資料，傳送資料庫 ===================================================================
    @PostMapping("/register/step4")
    public String registerStep4(
    		@RequestParam("gender") String genderr,        
    		@RequestParam("birth") String birthh,
            @RequestParam("phone") String phonee,
            @RequestParam("email") String email
     ) {
    	JSONObject responseBody = new JSONObject();
    	
    	gender = genderr;        // 使用genderr   變數儲存
    	birth = birthh;
    	phone = phonee;
    	
    	System.out.println("註冊 第四步: 註冊 性別、生日、號碼 ===================================");
        System.out.println("  變數儲存性別: " + genderr);    
        System.out.println("  變數儲存生日: " + birthh);          
        System.out.println("  變數儲存號碼: " + phonee);          
        System.out.println("  電子信箱: " + email);       
    	
    	responseBody.put("success", true);
        responseBody.put("message", "信箱、性別、生日、號碼已儲存到資料庫");
        return responseBody.toString();
    	
    }

 // 註冊 第五步:填寫個人資料，傳送資料庫 ===================================================================
    @PostMapping("/register/step5")
    public String registerStep5(
        @RequestParam("address") String address,
        @RequestParam("filess") MultipartFile avatarFile     
    ) {
        JSONObject responseBody = new JSONObject();

        System.out.println("註冊 第五步: 註冊所有個資 ========================");
        System.out.println("生日: " + birth);
        System.out.println("手機號碼: " + phone);
        System.out.println("地址: " + address);
        System.out.println("---------------------------------");
        System.out.println("電子信箱: " + email);
        System.out.println("加密過密碼: "+ password);
        System.out.println("用戶名: " +username);

        // 照片處理
        byte[] filess = null;
        try {
            if (avatarFile.isEmpty()) {
                responseBody.put("success", false);
                responseBody.put("message", "未上傳任何照片");
                return responseBody.toString();
            }
            try {
				filess = avatarFile.getBytes();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
        } catch (IOException e) {
            responseBody.put("success", false);
            responseBody.put("message", "照片處理失敗: " + e.getMessage());
            e.printStackTrace();  // 輸出堆疊跟踪以便調試
            return responseBody.toString();
        }

        // 將所有資料透過usersService的registerUser 存入資料庫
        try {
            usersService.registerUser(email, username, password, gender, birth, phone, address, filess);

            responseBody.put("success", true);
            responseBody.put("message", "註冊成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "註冊失敗：" + e.getMessage());
        }

        return responseBody.toString();
    }
    
        
// ‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵忘記密碼開始‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵  
// 說明: 當按下忘記密碼  1.使用者輸入email、系統發送驗證碼到使用者email  2.使用者輸入驗證碼  3.通過後->修改密碼2次一樣，跳轉登入
    
// 忘記密碼 第一步 檢查信箱、發送驗證碼==================================================================================             
    @PostMapping("/forgotPwdStep1")
    public String forgotPwdStep1(@RequestBody String body, HttpSession session) {
    	JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        email = obj.getString("email");

        // 檢查用戶是否已經存在
        if (!usersService.userExists(email)) {
            responseBody.put("success", false);
            responseBody.put("message", "帳號未註冊，請前往註冊");
            return responseBody.toString();
        }

        // 生成驗證碼並發送郵件
        verificationCode = VerificationCodeGenerator.generateVerificationCode();
        emailService.sendEmail(email, "驗證您的帳號", "您的驗證碼是: " + verificationCode);   
        
        // 將 email 和驗證碼存入 session
        session.setAttribute("email", email);
        session.setAttribute("verificationCode", verificationCode);

        responseBody.put("success", true);
        responseBody.put("message", "驗證碼已發送到您的郵箱");

        return responseBody.toString();
    }

   
// 忘記密碼 第二步 驗證驗證碼=======================================================================================  
    @PostMapping("/forgotPwdStep2")
    public String forgotPwdStep2(@RequestBody String body, HttpSession session) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        
        String savedVerificationCode = verificationCode;
        System.out.println("系統生成的savedVerificationCode:"+savedVerificationCode);
        
        // 從前端傳入的驗證碼
        String inputVerificationCode = obj.optString("inputVerificationCode", null); // 使用 optString 並指定預設值
        System.out.println("使用者輸入inputVerificationCode:"+inputVerificationCode);

        // 檢查 inputVerificationCode 是否為 null 或空
        if (inputVerificationCode == null || inputVerificationCode.isEmpty()) {
            responseBody.put("success", false);
            responseBody.put("message", "請輸入驗證碼");
            return responseBody.toString();
        }
        
        // 檢查是否從 session 中獲取到驗證碼
        if (savedVerificationCode == null) {
            responseBody.put("success", false);
            responseBody.put("message", "驗證碼已過期或無效");
            return responseBody.toString();
        }
        
        // 將使用者輸入的驗證碼與 session 中的驗證碼比對
        if (!inputVerificationCode.equals(savedVerificationCode)) {
            responseBody.put("success", false);
            responseBody.put("message", "驗證碼錯誤");
            return responseBody.toString();
        }
        
// ---------------------------測試用(信箱、使用者輸入的驗證碼、系統產生的驗證碼(儲存在session))-------------------------------------------
        System.out.println("===============忘記密碼 第二步 驗證驗證碼===============");
        System.out.println("email: "+email);

        // 清除驗證碼以防重複使用
        session.removeAttribute("verificationCode");
        // 將用戶電子郵件設置到 session
        session.setAttribute("email", email);

        responseBody.put("success", true);
        responseBody.put("message", "驗證碼驗證成功，資料已儲存");

        return responseBody.toString();
    }


  
// 忘記密碼 第三步 重設密碼=============================================================================    
    @PostMapping("/forgotPwdStep3")
    public String forgotPwdStep3(@RequestBody String body, HttpSession session) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);

        String newPassword = obj.optString("newPassword");
        String confirmPassword = obj.optString("confirmPassword");

        // 檢查新密碼與確認密碼是否一致
        if (!newPassword.equals(confirmPassword)) {
            responseBody.put("success", false);
            responseBody.put("message", "密碼不相符，請再輸入一次");
            return responseBody.toString();
        }

        // 根據 email 查詢用戶
        Users user = usersService.findByEmail(email);
        if (user == null) {
            responseBody.put("success", false);
            responseBody.put("message", "找不到該用戶");
            return responseBody.toString();
        }

        // 打印明文密碼以供測試
        System.out.println("===============忘記密碼 第三步 重設密碼===============");
        System.out.println("email: "+email);
        System.out.println("使用者輸入的明文密碼: " + newPassword);

        // 更新用戶密碼
        user.setPassword(newPassword);
        usersService.save(user);


        // 確認密碼已經成功更新
        Users updatedUser = usersService.findByEmail(email);
        String storedPassword = updatedUser.getPassword();
        System.out.println("儲存到資料庫的加密密碼: " + storedPassword);

        responseBody.put("success", true);
        responseBody.put("message", "密碼重設成功");
        return responseBody.toString();
    }




//登出 =================================================================================================
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        JSONObject responseBody = new JSONObject();
        HttpSession session = request.getSession(false);
        if (session != null) {         // 清除session內暫時儲存的用戶資訊
            session.invalidate();
        }
        
        // 清除客戶端的JWT，這裡假設JWT存在於Authorization header中
        response.setHeader("Authorization", "");
        
        responseBody.put("success", true);
        responseBody.put("message", "登出成功");
        return responseBody.toString();
    }
    
    
// ‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵編輯會員開始‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
// 說明: 點下編輯會員後 1.輸入密碼驗證  2.顯示會員所有原資料  3.開始更改資料，並即可傳送資料庫
    
// 驗證密碼 (編輯會員個人資料) ==================================================================
    @PostMapping("/verify-password")
    public String verifyPassword(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);        // 打包成JSON格式物件
        String email = obj.getString("email");        // 信箱驗證
        String password = obj.getString("password");  // 密碼驗證

        Users user = usersService.findByEmail(email); // 查找用戶 (查看是否存在用戶)
        if (user == null) {                           // 檢查目前網頁上是否有帳號存在
            responseBody.put("success", false);
            responseBody.put("message", "用戶不存在");
            return responseBody.toString();
        }

        // 驗證密碼 (使用者輸入密碼驗證，由於此狀態是已經登入的情況，前端sessionStorage已經儲存email)
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        if (!passwordMatches) {
            responseBody.put("success", false);
            responseBody.put("message", "密碼不正確");
            return responseBody.toString();
        }
        responseBody.put("success", true);
        responseBody.put("message", "密碼驗證成功");
        return responseBody.toString();
    }
    
    
// 顯示會員資料 ==============================================================================
    @GetMapping("/profile")
    public String getProfile(@RequestParam String userEmail) {
        JSONObject responseBody = new JSONObject();       // 打包成JSON格式物件      
        JSONArray array = new JSONArray();                // 使用陣列JSON格式 儲存資料庫取出的原資料      
        Users user = usersService.findByEmail(userEmail); // 連到usersService尋找該用戶的email
        if (user == null) {                               // 檢查目前網頁上是否有帳號存在
            responseBody.put("success", false);
            responseBody.put("message", "Email不存在");
            return responseBody.toString();       
        }
        JSONObject item = new JSONObject()                // JSON物件裡面放的資料
                .put("name", user.getUsername())
                .put("email", user.getEmail())
                .put("gender", user.getGender())
                .put("birth", user.getBirth())
                .put("phone", user.getPhone())
                .put("address", user.getAddress());
        array.put(item);                                  // 並且放在陣列內 
        responseBody.put("success", true);
        responseBody.put("user", array);

        return responseBody.toString();
    }

     
// 編輯會員個人資料 ===========================================================================
    @PutMapping("/modify")
    public ResponseEntity<String> modify(            // 因為要上傳照片 前端要用form表單
        @RequestParam("username") String username,   // 所以後端要使用 RequestParam接收表單數據
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("gender") String gender,
        @RequestParam("birth") String birth,
        @RequestParam("phone") String phone,
        @RequestParam("address") String address,      // 以下為上傳檔案(照片)
        @RequestParam(value = "avatar", required = false) MultipartFile avatarFile  
    ) {
        JSONObject responseBody = new JSONObject();   // 用於存儲 API 回應的內容，可以將修改後的資料以JSON格式返回給客戶
        
        if (email == null || email.isEmpty()) {       // 檢查 email 是否存在(空值、無此帳號)
            responseBody.put("success", false);
            responseBody.put("message", "Email是必要欄位");
            return ResponseEntity.badRequest().body(responseBody.toString());
        }

        // 檢查用戶名是否已被使用
        Users existingUserByUsername = usersService.findByUsername(username);
        if (existingUserByUsername != null && !existingUserByUsername.getEmail().equals(email)) {
            responseBody.put("success", false);
            responseBody.put("message", "此用戶名已被註冊");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody.toString());
        }
        // 將更改後的資料傳給usersService，由他去編輯資料庫
        Users user = usersService.modifyByEmail(email, username, password, gender, birth, phone, address, avatarFile);

        // 檢查目前網頁上用戶是否存在
        if (user == null) {               
            responseBody.put("success", false);
            responseBody.put("message", "Email不存在或修改失敗");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody.toString());
        }

        responseBody.put("success", true);
        responseBody.put("message", "修改成功");
        responseBody.put("data", new JSONObject(user));
        return ResponseEntity.ok(responseBody.toString());
    }

}
  