package com.cloth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloth.Repository.OrderDetailRepository;
import com.cloth.model.Cart;
import com.cloth.model.Orderdetail;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	

//    public List<Orderdetail> findOrderdetailByUserId(Integer userId) {
//        return orderDetailRepository.findOrderdetailByUserId(userId);
        
    //}
}
