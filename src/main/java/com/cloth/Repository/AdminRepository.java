package com.cloth.Repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.cloth.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	

//	Admin findByAdmin_name(String admin_name);
}
