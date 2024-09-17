package com.groupmeeting.global.exception.custom;

public class NotImageRequestException extends Exception {
    public NotImageRequestException(String message){
        super(message);
    }
    public NotImageRequestException(){
        super();
    }
}
