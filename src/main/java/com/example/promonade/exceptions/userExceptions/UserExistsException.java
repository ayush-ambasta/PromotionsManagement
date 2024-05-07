package com.example.promonade.exceptions.userExceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message){
        super(message);
    }
}
