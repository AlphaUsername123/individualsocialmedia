package com.example.be.Business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PostAlreadyExistsException extends ResponseStatusException {
    public PostAlreadyExistsException(String errorCause) {
        super(HttpStatus.CONFLICT, errorCause);
    }
}
