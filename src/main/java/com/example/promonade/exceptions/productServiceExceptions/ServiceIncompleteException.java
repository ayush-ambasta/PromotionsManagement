package com.example.promonade.exceptions.productServiceExceptions;

public class ServiceIncompleteException extends RuntimeException{
    public ServiceIncompleteException(String message){
        super(message);
    }
}
