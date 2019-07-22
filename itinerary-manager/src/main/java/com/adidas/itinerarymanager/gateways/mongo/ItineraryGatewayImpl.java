package com.adidas.itinerarymanager.gateways.mongo;

import com.adidas.itinerarymanager.domains.Itinerary;
import com.adidas.itinerarymanager.gateways.ItineraryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItineraryGatewayImpl implements ItineraryGateway {

    private final ItineraryRepository itineraryRepository;

    @Override
    public Itinerary create(final Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public Itinerary update(final Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public void delete(final String id) {
        itineraryRepository.deleteById(id);
    }

    @Override
    public Optional<Itinerary> findById(final String id) {
        return itineraryRepository.findById(id);
    }

    @Override
    public Optional<Itinerary> findByCityAndDestinyCity(final String city, final String destinyCity) {
        return itineraryRepository.findByCityAndDestinyCity(city, destinyCity);
    }

    @Override
    public Collection<Itinerary> list() {
        return itineraryRepository.findAll();
    }

    @Override
    public Collection<Itinerary> findByCity(final String city) {
        return itineraryRepository.findByCity(city);
    }
}
