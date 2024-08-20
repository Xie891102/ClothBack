package com.cloth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloth.Repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	

//    public List<Orderdetail> findOrderdetailByUserId(Integer userId) {
//        return orderDetailRepository.findOrderdetailByUserId(userId);
        
    //}
}
