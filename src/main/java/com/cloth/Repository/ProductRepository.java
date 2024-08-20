package com.cloth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloth.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	 
	@Query("SELECT p FROM Product p WHERE (:size IS NULL OR p.size = :size) AND (:style IS NULL OR p.style = :style) AND (:color IS NULL OR p.color = :color)")
	List<Product> findBySizeAndStyleAndColor(@Param("size") String size, @Param("style") String style, @Param("color") String color);

}
