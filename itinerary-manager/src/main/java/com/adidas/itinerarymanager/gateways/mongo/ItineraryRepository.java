package com.adidas.itinerarymanager.gateways.mongo;

import com.adidas.itinerarymanager.domains.Itinerary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface ItineraryRepository extends MongoRepository<Itinerary, String> {

    Optional<Itinerary> findByCityAndDestinyCity(String city, String destinyCity);

    Collection<Itinerary> findByCity(String city);
}
