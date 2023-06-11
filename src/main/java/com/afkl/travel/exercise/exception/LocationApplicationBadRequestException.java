package com.afkl.travel.exercise.exception;

public class LocationApplicationBadRequestException extends RuntimeException{

    public LocationApplicationBadRequestException(String message) {
        super(message);
    }

    public LocationApplicationBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
