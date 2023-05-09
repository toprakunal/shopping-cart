package com.example.TomDemo2.exception;

import java.util.function.Supplier;

public class CartNotFoundException extends RuntimeException  {

    public CartNotFoundException(String message) {
        super(message);
    }


}
