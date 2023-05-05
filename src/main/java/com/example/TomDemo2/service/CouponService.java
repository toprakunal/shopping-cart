package com.example.TomDemo2.service;


import com.example.TomDemo2.model.Coupon;
import com.example.TomDemo2.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }


    public Optional<Coupon> findCouponById(int couponId){
        return couponRepository.findById(couponId);
    }

    public List<Coupon> findAllCoupon(){
        return couponRepository.findAll();
    }
    public void save(Coupon coupon){
         couponRepository.save(coupon);
    }
}
