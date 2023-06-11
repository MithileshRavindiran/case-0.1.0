package com.afkl.travel.exercise.exception;

public class LocationApplicationNotFoundException extends RuntimeException{

    public LocationApplicationNotFoundException(String message) {
        super(message);
    }

    public LocationApplicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
