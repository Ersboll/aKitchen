package com.dtu.akitchen.authentication;

public class UserNotSignedInException extends Exception{
    public UserNotSignedInException(String errorMessage){
        super(errorMessage);
    }
}
