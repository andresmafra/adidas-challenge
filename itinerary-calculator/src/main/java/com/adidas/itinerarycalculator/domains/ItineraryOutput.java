package com.adidas.itinerarycalculator.domains;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Collection;

@Data
public class ItineraryOutput {

    private String city;

    private String destinyCity;

    private Long travelTime;

    private Collection<String> itineraries = Lists.newArrayList();
}
