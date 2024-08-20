package com.cloth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloth.model.Orderdetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<Orderdetail, Integer> {
	 Orderdetail findByCartId(Integer cartId);

	
	 List<Orderdetail> findByCartUsersId(Integer userId);

     List<Orderdetail> findByCartUsersIdAndCartProductId(Integer userId, Integer productId);
}
