package com.adidas.itinerarycalculator.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class PathSummary {

    private Long pathMinutes;

    private Integer pathSize;

    private Set<Itinerary> itineraries;

}
