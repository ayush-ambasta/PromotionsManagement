package com.example.promonade.exceptions.customerExceptions;

public class CustomerNotFoundException extends RuntimeException {
    public  CustomerNotFoundException(String message)
    {
        super(message);
    }
}
