package com.example.promonade.exceptions.userExceptions;

public class TeamNotExistsException extends RuntimeException{
    public TeamNotExistsException(String message){
        super(message);
    }
}
