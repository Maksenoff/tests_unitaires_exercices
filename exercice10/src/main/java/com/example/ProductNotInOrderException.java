package com.example;

public class ProductNotInOrderException extends RuntimeException {
    public ProductNotInOrderException(String message) {
        super(message);
    }
}
