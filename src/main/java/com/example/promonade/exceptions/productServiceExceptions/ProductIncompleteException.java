package com.example.promonade.exceptions.productServiceExceptions;

public class ProductIncompleteException extends RuntimeException{
    public ProductIncompleteException(String message){
        super(message);
    }
}
