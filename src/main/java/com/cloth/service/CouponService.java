package com.cloth.service;

import com.cloth.model.Coupon;
import com.cloth.model.Couponowner;
import com.cloth.Repository.CouponRepository;
import com.cloth.Repository.CouponownerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponownerRepository couponownerRepository;
    
    @Autowired
    private CouponRepository couponRepository;

    public List<Couponowner> getActiveCouponOwnersByUserId(Integer userId) {
        return couponownerRepository.findActiveCouponsByUserId(userId);
    }

}
