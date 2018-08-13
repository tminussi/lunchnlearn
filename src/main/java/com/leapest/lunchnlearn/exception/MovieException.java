package com.leapest.lunchnlearn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieException extends ResponseStatusException {

    public MovieException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
