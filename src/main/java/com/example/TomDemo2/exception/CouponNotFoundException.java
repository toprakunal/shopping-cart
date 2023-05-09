package com.example.TomDemo2.exception;

public class CouponNotFoundException extends RuntimeException{

    public CouponNotFoundException(String message) {
        super(message);
    }
}
