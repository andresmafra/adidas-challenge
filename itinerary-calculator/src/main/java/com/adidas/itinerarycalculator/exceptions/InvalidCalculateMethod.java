package com.adidas.itinerarycalculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCalculateMethod extends RuntimeException {

    public InvalidCalculateMethod(final String message) {
        super(message);
    }
}
