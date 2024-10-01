package com.groupmeeting.core.exception.custom;

public class NotImageRequestException extends Exception {
    public NotImageRequestException(String message){
        super(message);
    }
    public NotImageRequestException(){
        super();
    }
}
