package com.leapest.lunchnlearn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieAlreadyExistsException extends ResponseStatusException {

    public MovieAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
