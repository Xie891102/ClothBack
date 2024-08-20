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
public class CouponService {

    @Autowired
    private CouponownerRepository couponownerRepository;
    
    @Autowired
    private CouponRepository couponRepository;
    

    public List<Coupon> getAvailableCoupons() {
        return couponRepository.findAvailableCoupons();
    }

    @Transactional
    public boolean claimCoupon(Integer usersId, Integer couponId) {
        // Check if the user has already claimed the coupon
        boolean alreadyClaimed = couponownerRepository.existsByUsersIdAndCouponId(usersId, couponId);
        if (alreadyClaimed) {
            return false; // Already claimed
        }

        // Get the Coupon information
        Coupon coupon = couponRepository.findById(couponId).orElse(null);
        if (coupon == null) {
            return false; // Coupon does not exist
        }

        // Create and save Couponowner instance
        Couponowner couponowner = new Couponowner();
        Users user = new Users(); // Create Users object
        user.setId(usersId); // Set user ID
        couponowner.setUsers(user);
        couponowner.setCoupon(coupon);
        couponowner.setStatus(1); // Set status as claimed
        couponowner.setStart_at(new Date());
        couponowner.setEnd_at(coupon.getEnd_at()); // Set validity end date

        couponownerRepository.save(couponowner);
        return true;
    }

    private Date getCouponEndDate(Date couponEndDate) {
        // Set the end date to the original coupon's end date
        return couponEndDate;
    }

    @Transactional
    public List<Couponowner> getActiveCouponOwnersByUserId(Integer userId) {
        return couponownerRepository.findActiveCouponsByUserId(userId);
    }

}
