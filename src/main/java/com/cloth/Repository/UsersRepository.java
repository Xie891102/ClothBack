package com.cloth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloth.model.Users;

//UsersRepository 介面，繼承自 JpaRepository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	// 自定義查詢方法，根據用戶名查找用戶
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Optional<Users> findByUsername(@Param("username") String username);
    
    // 自定義查詢方法，根據電子郵件查找用戶
    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findByEmail(@Param("email") String email);
    
}
