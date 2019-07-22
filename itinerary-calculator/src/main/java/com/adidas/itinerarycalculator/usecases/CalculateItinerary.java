package com.adidas.itinerarycalculator.usecases;

import com.adidas.itinerarycalculator.domains.CalculateMode;
import com.adidas.itinerarycalculator.domains.Itinerary;
import com.adidas.itinerarycalculator.domains.ItineraryOutput;
import com.adidas.itinerarycalculator.domains.PathSummary;
import com.adidas.itinerarycalculator.exceptions.ResourceNotFoundException;
import com.adidas.itinerarycalculator.gateways.ItineraryManagerGateway;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.adidas.itinerarycalculator.domains.CalculateMode.CONNECTIONS;
import static com.adidas.itinerarycalculator.domains.CalculateMode.TIME;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculateItinerary {

    private final ItineraryManagerGateway itineraryManagerGateway;

    public ItineraryOutput calculate(final String from, final String to, final CalculateMode mode) {
        final Collection<Itinerary> initialItinerary = itineraryManagerGateway.findAllByCity(from);

        raiseNotFoundIfEmpty(from, to, initialItinerary);

        log.info("Working with this initial itineraries: {}", initialItinerary);

        final ItineraryOutput output = new ItineraryOutput();
        output.setCity(from);
        output.setDestinyCity(to);

        final Optional<Itinerary> destiny = getDestiny(initialItinerary, to);
        if (destiny.isPresent()) {
            log.info("Destiny is found withou any city connection");
            output.setTravelTime(Itinerary.calculateDuration(destiny.get()));
        } else {
            final Set<Set<Itinerary>> paths = buildItineraryPaths(initialItinerary, from, to);
            raiseNotFoundIfEmpty(from, to, paths);
            log.info("All itinerary paths was build: {}", paths);
            final PathSummary winnerPath = getWinnerPath(mode, buildPathSummary(paths));
            output.setTravelTime(winnerPath.getPathMinutes());
            output.setItineraries(getItineraryCityName(winnerPath));
        }

        log.debug("Itinerary output: {}", output);
        return output;
    }

    private PathSummary getWinnerPath(final CalculateMode mode, final Collection<PathSummary> pathSummaries) {
        PathSummary winnerPath = null;
        if (CONNECTIONS.equals(mode)) {
            winnerPath = pathSummaries.stream()
                    .sorted(Comparator.comparing(PathSummary::getPathSize))
                    .findFirst().get();
        } else if (TIME.equals(mode)) {
            winnerPath = pathSummaries.stream()
                    .sorted(Comparator.comparing(PathSummary::getPathMinutes))
                    .findFirst().get();
        }
        log.debug("The winner paths is found: {}", winnerPath);
        return winnerPath;
    }

    private List<String> getItineraryCityName(final PathSummary winnerPath) {
        return winnerPath.getItineraries()
                .stream()
                .map(Itinerary::getDestinyCity)
                .collect(toList());
    }

    private Collection<PathSummary> buildPathSummary(final Set<Set<Itinerary>> paths) {
        return paths.stream()
                .map(itineraries ->
                        PathSummary.builder()
                                .pathMinutes(sumTotalMinutes(itineraries))
                                .pathSize(itineraries.size())
                                .itineraries(itineraries)
                                .build())
                .collect(toList());
    }

    private Set<Set<Itinerary>> buildItineraryPaths(final Collection<Itinerary> itineraries,
                                                    final String from, final String to) {
        final Set<Set<Itinerary>> itineraryPaths = Sets.newLinkedHashSet();
        itineraries.forEach(itinerary -> {
            final Set<Itinerary> itineraryPath = Sets.newLinkedHashSet();
            buildNextCity(from, to, itinerary, itineraryPath);
            if (hasDestinyCityOnPath(itineraryPath, to)) {
                itineraryPaths.add(itineraryPath);
            }
        });
        return itineraryPaths;
    }

    private boolean hasDestinyCityOnPath(final Set<Itinerary> itineraryPath, final String to) {
        return itineraryPath
                .stream()
                .anyMatch(itinerary -> itinerary.getDestinyCity().equals(to));
    }

    private void buildNextCity(final String from, final String to,
                               final Itinerary itinerary, final Collection<Itinerary> itineraryPath) {

        itineraryPath.add(itinerary);

        final Collection<Itinerary> nextCities =
                itineraryManagerGateway.findAllByCity(itinerary.getDestinyCity())
                        .stream()
                        .filter(nextCity -> !nextCity.getDestinyCity().equals(from))
                        .collect(toList());

        final Optional<Itinerary> destiny = getDestiny(nextCities, to);

        if (!destiny.isPresent()) {
            nextCities.forEach(nextCity -> buildNextCity(from, to, nextCity, itineraryPath));
        } else {
            itineraryPath.add(destiny.get());
        }
    }

    private Long sumTotalMinutes(final Set<Itinerary> itineraries) {
        return itineraries
                .stream()
                .mapToLong(Itinerary::calculateDuration)
                .sum();
    }

    private Optional<Itinerary> getDestiny(final Collection<Itinerary> itineraries, final String to) {
        return itineraries
                .stream()
                .filter(itinerary -> itinerary.getDestinyCity().equals(to))
                .findFirst();
    }

    private void raiseNotFoundIfEmpty(final String from, final String to, final Collection<?> coll) {
        if (coll.isEmpty()) {
            throw new ResourceNotFoundException(format("Itinerary not found from city %s to %s", from, to));
        }
    }

}
