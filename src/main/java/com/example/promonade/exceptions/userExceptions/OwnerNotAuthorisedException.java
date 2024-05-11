package com.example.promonade.exceptions.userExceptions;

public class OwnerNotAuthorisedException extends RuntimeException{
    public OwnerNotAuthorisedException(String message){
        super(message);
    }
}
