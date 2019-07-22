package com.adidas.itinerarymanager.usecases;

import com.adidas.itinerarymanager.domains.Itinerary;
import com.adidas.itinerarymanager.exceptions.ItineraryAlreadyExistsException;
import com.adidas.itinerarymanager.exceptions.ItineraryNotFoundException;
import com.adidas.itinerarymanager.gateways.ItineraryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrudItinerary {

    private final ItineraryGateway itineraryGateway;

    public Itinerary create(final Itinerary itinerary) {
        final Optional<Itinerary> itineraryOptional =
                itineraryGateway.findByCityAndDestinyCity(itinerary.getCity(), itinerary.getDestinyCity());

        itineraryOptional.ifPresent(it -> {
            log.warn("Itinerary already exists with city: {} and destinyCity: {}",
                    itinerary.getCity(), itinerary.getDestinyCity());
            throw new ItineraryAlreadyExistsException(format("Itinerary already exists with city %s and destinyCity %s",
                    itinerary.getCity(), itinerary.getDestinyCity()));
        });

        return itineraryGateway.create(itinerary);
    }

    public Itinerary update(final String id, final Itinerary itinerary) {
        final Optional<Itinerary> itineraryOptional = itineraryGateway.findById(id);
        final Itinerary dbItinerary =
                itineraryOptional.orElseThrow(() -> new ItineraryNotFoundException(format("Itinerary not found with id %s", id)));

        final Optional<Itinerary> byCityAndDestinyCityOptional =
                itineraryGateway.findByCityAndDestinyCity(itinerary.getCity(), itinerary.getDestinyCity());

        byCityAndDestinyCityOptional.ifPresent(byCityAndDestinyCity -> {
            if (!byCityAndDestinyCity.getId().equals(id)) {
                log.warn("Itinerary already exists with city: {} and destinyCity: {}",
                        itinerary.getCity(), itinerary.getDestinyCity());
                throw new ItineraryAlreadyExistsException(format("Itinerary already exists with city %s and destinyCity %s",
                        itinerary.getCity(), itinerary.getDestinyCity()));
            }
        });

        dbItinerary.setCity(itinerary.getCity());
        dbItinerary.setDestinyCity(itinerary.getDestinyCity());
        dbItinerary.setArrivalTime(itinerary.getArrivalTime());
        dbItinerary.setDepartureTime(itinerary.getDepartureTime());

        return itineraryGateway.update(dbItinerary);
    }

    public void delete(final String id) {
        final Optional<Itinerary> itineraryOptional = itineraryGateway.findById(id);
        itineraryOptional.orElseThrow(() -> new ItineraryNotFoundException(format("Itinerary not found with id %s", id)));
        itineraryGateway.delete(id);
    }

    public Collection<Itinerary> list() {
        return itineraryGateway.list();
    }

    public Collection<Itinerary> findAllByCity(final String city) {
        return itineraryGateway.findByCity(city);
    }
}
