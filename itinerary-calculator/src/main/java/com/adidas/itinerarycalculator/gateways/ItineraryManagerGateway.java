package com.adidas.itinerarycalculator.gateways;

import com.adidas.itinerarycalculator.domains.Itinerary;

import java.util.Collection;

public interface ItineraryManagerGateway {

    Collection<Itinerary> findAllByCity(String city);
}
