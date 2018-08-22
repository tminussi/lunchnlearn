package com.leapest.lunchnlearn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieNotFoundException extends ResponseStatusException {

    public MovieNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
