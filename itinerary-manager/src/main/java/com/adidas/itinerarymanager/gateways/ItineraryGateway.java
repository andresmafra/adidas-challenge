package com.adidas.itinerarymanager.gateways;

import com.adidas.itinerarymanager.domains.Itinerary;

import java.util.Collection;
import java.util.Optional;

public interface ItineraryGateway {

    Itinerary create(Itinerary itinerary);

    Itinerary update(Itinerary itinerary);

    void delete(String id);

    Optional<Itinerary> findById(String id);

    Optional<Itinerary> findByCityAndDestinyCity(String city, String destinyCity);

    Collection<Itinerary> list();

    Collection<Itinerary> findByCity(String city);
}
