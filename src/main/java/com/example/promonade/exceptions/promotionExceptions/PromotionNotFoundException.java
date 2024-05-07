package com.example.promonade.exceptions.promotionExceptions;

public class PromotionNotFoundException extends RuntimeException{
    public PromotionNotFoundException(String message){
        super(message);
    }
}
