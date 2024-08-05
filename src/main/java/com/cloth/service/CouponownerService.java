package com.cloth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloth.Repository.CouponRepository;
import com.cloth.Repository.CouponownerRepository;
import com.cloth.model.Couponowner;


@Service
public class CouponownerService {

	@Autowired
	private CouponownerRepository couponownerRepository;
	
    //更改status 
//    public void updateCouponStatusToZero(List<Integer> ids) {
//        ids.forEach(id -> couponownerRepository.updateCouponStatusToZero(id));
//    }
    
    @Transactional // Ensure this is present
    public void updateCouponStatusToZero(List<Integer> ids) {
        try {
            ids.forEach(id -> couponownerRepository.updateCouponStatusToZero(id));
        } catch (Exception e) {
            // Error handling
            e.printStackTrace();
            throw new RuntimeException("Error updating coupon status", e);
        }
    }
 
}
