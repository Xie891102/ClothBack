package com.cloth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cloth.model.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	 @Query("SELECT c FROM Coupon c WHERE c.start_at <= CURRENT_DATE AND c.end_at >= CURRENT_DATE")
	    List<Coupon> findAvailableCoupons();
	}
