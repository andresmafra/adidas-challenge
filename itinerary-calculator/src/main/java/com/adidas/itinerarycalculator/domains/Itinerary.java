package com.adidas.itinerarycalculator.domains;

import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

@Data
public class Itinerary {

    private String id;

    private String city;

    private String destinyCity;

    private String departureTime;

    private String arrivalTime;

    public static long calculateDuration(final Itinerary i) {
        return Duration.between(LocalTime.parse(i.getDepartureTime()), LocalTime.parse(i.getArrivalTime())).toMinutes();
    }
}
