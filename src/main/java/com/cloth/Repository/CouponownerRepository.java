package com.cloth.Repository;

import com.cloth.model.Couponowner;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponownerRepository extends JpaRepository<Couponowner, Integer> {


	@Query("SELECT co FROM Couponowner co WHERE co.users.id = :userId AND co.status = 1")
    List<Couponowner> findActiveCouponsByUserId(@Param("userId") Integer userId);
	
	   //改成status=0,已結帳
	@Modifying
	@Query("UPDATE Couponowner c SET c.status = 0 WHERE c.id = :id")
	void updateCouponStatusToZero(@Param("id") Integer id);
	
	

	@Query("SELECT c FROM Couponowner c WHERE c.id = :id")
	Optional<Couponowner> findById(@Param("id") Couponowner couponId);
	
	// boolean existsByUsersIdAndCouponId(Integer usersId, Integer couponId);

    boolean existsByUsersIdAndCouponId(Integer usersId, Integer couponId);

}
