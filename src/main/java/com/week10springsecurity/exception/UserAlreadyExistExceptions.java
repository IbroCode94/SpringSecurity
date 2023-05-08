package com.week10springsecurity.exception;

public class UserAlreadyExistExceptions extends RuntimeException{
    public UserAlreadyExistExceptions(String message){
        super(message);
    }
}
