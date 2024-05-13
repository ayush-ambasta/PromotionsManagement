package com.example.promonade.exceptions.productServiceExceptions;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message){
        super(message);
    }
}
