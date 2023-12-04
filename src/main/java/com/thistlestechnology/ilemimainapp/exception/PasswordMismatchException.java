package com.thistlestechnology.ilemimainapp.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message){
        super(message);
    }

}
