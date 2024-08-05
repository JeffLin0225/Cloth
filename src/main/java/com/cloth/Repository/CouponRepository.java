package com.cloth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloth.model.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {


}
