package com.cloth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloth.model.Orders;

public interface OrdersRepository extends JpaRepository <Orders, Integer>{
	
	@Query("SELECT c FROM Orders c WHERE c.users.id = :userId")
    List<Orders> findOrdersByUserId(@Param("userId") Integer userId);
	
	@Query("SELECT o FROM Orders o WHERE o.id = :id")
	List<Orders> findOrderById(@Param("id") int id);
}
