package com.adidas.itinerarymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }
}
