package com.adidas.itinerarymanager.gateways;

import com.adidas.itinerarymanager.domains.Itinerary;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItineraryGatewayInMemoryImpl implements ItineraryGateway {

    private final Map<String, Itinerary> repository = Maps.newHashMap();

    @Override
    public Itinerary create(final Itinerary itinerary) {
        repository.put(itinerary.getId(), itinerary);
        return itinerary;
    }

    @Override
    public Itinerary update(final Itinerary itinerary) {
        delete(itinerary.getId());
        create(itinerary);
        return itinerary;
    }

    @Override
    public void delete(final String id) {
        repository.remove(id);
    }

    @Override
    public Optional<Itinerary> findById(final String id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Optional<Itinerary> findByCityAndDestinyCity(final String city, final String destinyCity) {
        return repository.values().stream().filter(itinerary ->
                itinerary.getCity().equalsIgnoreCase(city) &&
                        itinerary.getDestinyCity().equalsIgnoreCase(destinyCity)
        ).findFirst();
    }

    @Override
    public Collection<Itinerary> list() {
        return repository.values();
    }

    public void clear() {
        repository.clear();
    }

    @Override
    public Collection<Itinerary> findByCity(final String city) {
        return repository.values().stream().filter(itinerary ->
                itinerary.getCity().equalsIgnoreCase(city)
        ).collect(Collectors.toList());
    }
}
