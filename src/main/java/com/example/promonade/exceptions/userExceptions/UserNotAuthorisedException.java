package com.example.promonade.exceptions.userExceptions;

public class UserNotAuthorisedException extends RuntimeException{
    public UserNotAuthorisedException(String message){
        super(message);
    }
}
