package com.example.promonade.exceptions.userExceptions;

public class RoleNotExistsException extends RuntimeException{
    public RoleNotExistsException(String message){
        super(message);
    }
}
