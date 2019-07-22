package com.adidas.itinerarycalculator.gateways.feign;

import com.adidas.itinerarycalculator.domains.Itinerary;
import com.adidas.itinerarycalculator.gateways.ItineraryManagerGateway;
import com.adidas.itinerarycalculator.gateways.feign.json.QueryJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ItineraryManagerGatewayImpl implements ItineraryManagerGateway {

    private final ItineraryManagerFeign itineraryManagerFeign;

    @Override
    public Collection<Itinerary> findAllByCity(final String city) {
        return itineraryManagerFeign.queryByCity(QueryJson.builder().city(city).build());
    }
}
