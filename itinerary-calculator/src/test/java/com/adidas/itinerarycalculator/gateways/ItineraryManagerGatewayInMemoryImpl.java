package com.adidas.itinerarycalculator.gateways;

import com.adidas.itinerarycalculator.domains.Itinerary;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.stream.Collectors;

public class ItineraryManagerGatewayInMemoryImpl implements ItineraryManagerGateway {

    private final Collection<Itinerary> itineraries = Lists.newArrayList();

    @Override
    public Collection<Itinerary> findAllByCity(final String city) {
        return itineraries.stream()
                .filter(itinerary -> itinerary.getCity().equals(city))
                .collect(Collectors.toList());
    }

    public void clear() {
        itineraries.clear();
    }

    public void add(final Itinerary itinerary) {
        itineraries.add(itinerary);
    }
}
