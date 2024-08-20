package com.cloth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cloth.model.Coupon;
import com.cloth.service.CouponService;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/available")
    public ResponseEntity<String> getAvailableCoupons() {
        try {
            List<Coupon> coupons = couponService.getAvailableCoupons();

            // 创建 JSON 响应
            JSONArray responseArray = new JSONArray();
            for (Coupon coupon : coupons) {
                JSONObject couponJson = new JSONObject();
                couponJson.put("id", coupon.getId());
                couponJson.put("name", coupon.getName());
                couponJson.put("description", coupon.getDescription());
                couponJson.put("discount", coupon.getDiscount());
                couponJson.put("quantity", coupon.getQuantity());
                couponJson.put("start_at", coupon.getStart_at());
                couponJson.put("end_at", coupon.getEnd_at());
                couponJson.put("created_by", coupon.getCreated_by());
                couponJson.put("created_at", coupon.getCreated_at());
                couponJson.put("updated_by", coupon.getUpdated_by());
                couponJson.put("updated_at", coupon.getUpdated_at());

                responseArray.put(couponJson);
            }

            return ResponseEntity.ok(responseArray.toString());
        } catch (JSONException e) {
            // 处理 JSONException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error creating JSON response");
        }
    }

    @PostMapping("/claim")
    public ResponseEntity<String> claimCoupon(
            @RequestParam Integer userId,
            @RequestParam Integer couponId) {
        try {
            boolean claimed = couponService.claimCoupon(userId, couponId);
            if (claimed) {
                return ResponseEntity.ok("Coupon claimed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Coupon already claimed or invalid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error claiming coupon: " + e.getMessage());
        }
    }
}


