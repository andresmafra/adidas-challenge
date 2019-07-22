package com.adidas.itinerarymanager.exceptions;

public class ItineraryAlreadyExistsException extends BusinessException {

    public ItineraryAlreadyExistsException(final String message) {
        super(message);
    }
}
