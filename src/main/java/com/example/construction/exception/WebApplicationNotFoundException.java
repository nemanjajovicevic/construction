package com.example.construction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WebApplicationNotFoundException extends RuntimeException {
    public WebApplicationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
