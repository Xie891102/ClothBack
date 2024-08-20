package com.cloth.Controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloth.service.CouponownerService;

@RestController
@RequestMapping("/api/couponowner")
@CrossOrigin
public class CouponownerController {
	
    @Autowired
    private final CouponownerService couponownerService;


    public CouponownerController(CouponownerService couponownerService) {
        this.couponownerService = couponownerService;
    }

    @PostMapping("/user/{userId}")
    public String addCouponForUser(@PathVariable Integer userId, @RequestBody String jsonBody) {
        JSONObject jsonObject = new JSONObject(jsonBody);
        // 根據前端發送的 JSON 數據進行相應處理，例如解析後添加到用戶的優惠券列表中
        // 這裡只是示例，具體邏輯需要根據你的需求來實現
        String couponCode = jsonObject.getString("couponCode");
        // 其他處理邏輯...

        return "Coupon added successfully";
    }
    
    //更改coupon status 
    @PutMapping("/status")
    public ResponseEntity<Void> updateCouponStatus(@RequestBody List<Integer> ids) {
        try {
            couponownerService.updateCouponStatusToZero(ids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Error handling
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

