package com.example.promonade.exceptions.tokenExceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String message){
        super(message);
    }
}
