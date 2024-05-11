package com.example.promonade.exceptions.userExceptions;

public class TeamNotAuthorisedException extends RuntimeException{
    public TeamNotAuthorisedException(String message){
        super(message);
    }
}
