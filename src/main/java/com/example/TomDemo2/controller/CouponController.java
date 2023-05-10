package com.example.TomDemo2.controller;

import com.example.TomDemo2.model.Coupon;
import com.example.TomDemo2.service.CouponService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping()
    public List<Coupon> findAllCoupon(){
        return couponService.findAllCoupon();
    }


}
