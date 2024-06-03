package com.example.promonade.exceptions;

import com.example.promonade.dto.response.MessageResponse;

import com.example.promonade.exceptions.customerExceptions.PurchasedProductsandServicesNotFound;
import com.example.promonade.exceptions.productServiceExceptions.ProductIncompleteException;
import com.example.promonade.exceptions.productServiceExceptions.ProductNotFoundException;
import com.example.promonade.exceptions.productServiceExceptions.ServiceIncompleteException;
import com.example.promonade.exceptions.productServiceExceptions.ServiceNotFoundException;
import com.example.promonade.exceptions.customerExceptions.CustomerNotFoundException;
import com.example.promonade.exceptions.promotionExceptions.PromotionIncompleteException;
import com.example.promonade.exceptions.promotionExceptions.PromotionNotFoundException;
import com.example.promonade.exceptions.tokenExceptions.TokenExpiredException;
import com.example.promonade.exceptions.userExceptions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            PromotionIncompleteException.class,
            RoleNotExistsException.class,
            UserExistsException.class,
            PromotionNotFoundException.class,
            TeamNotAuthorisedException.class,
            TeamNotExistsException.class,
            UserNotFoundException.class,
            UserNotAuthorisedException.class,
            ProductIncompleteException.class,
            ProductNotFoundException.class,
            ServiceIncompleteException.class,
            ServiceNotFoundException.class,
            ServiceNotFoundException.class,
            CustomerNotFoundException.class,
            PurchasedProductsandServicesNotFound.class
    })
    public final ResponseEntity<?> handeException(RuntimeException ex, WebRequest request){
        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(ex.toString()));
    }
    @ExceptionHandler({TokenExpiredException.class})
    public final ResponseEntity<?> tokenException(RuntimeException ex, WebRequest request){
        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        return ResponseEntity
                .status(440)
                .body(new MessageResponse(ex.toString()));
    }
}
