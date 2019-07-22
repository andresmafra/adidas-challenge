package com.adidas.itinerarymanager.exceptions;


public class ItineraryNotFoundException extends ResourceNotFoundException {

    public ItineraryNotFoundException(final String message) {
        super(message);
    }
}
