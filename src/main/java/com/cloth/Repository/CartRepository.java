package com.cloth.Repository;

import com.cloth.model.Cart;
import com.cloth.model.Couponowner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Query("SELECT c FROM Cart c WHERE c.users.id = :userId")
    List<Cart> findCartsByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT c FROM Cart c WHERE c.users.id = :userId AND c.status = :status")
    List<Cart> findByUsersIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);
    
    //改成status=2,未勾選
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.status = 2 WHERE c.id = :id")
    void updateStatusToTwo(Integer id);

    //改成status=0,已勾選
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.status = 0 WHERE c.id = :id")
    void updateStatusToZero(Integer id);
  
    //改成status=1,已結帳
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.status = 1 WHERE c.id = :id")
    void updateStatusToOne(Integer id);

	Optional<Couponowner> findById(Cart cart);

	 @Query("SELECT c FROM Cart c WHERE c.product.id = :productId AND c.users.id = :userId AND c.status <> :status")
	    Cart findByProductIdAndUserIdAndStatusNot(@Param("productId") Integer productId, 
	                                              @Param("userId") Integer userId, 
	                                              @Param("status") Integer status);
    
    
}
