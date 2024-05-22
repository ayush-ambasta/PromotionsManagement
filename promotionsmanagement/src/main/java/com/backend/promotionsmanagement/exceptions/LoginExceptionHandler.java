package com.backend.promotionsmanagement.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginExceptionHandler {


    @ExceptionHandler(UserNameNotFoundException.class)
    public String handleUserNameNotFoundException()
    {
        return "Username doesn't exist!!!";
    }


    @ExceptionHandler(PasswordMismatchException.class)
    public String handlePasswordMismatchException()
    {
        return "Password doesn't match";
    }

}
