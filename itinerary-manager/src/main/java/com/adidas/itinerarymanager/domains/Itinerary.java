package com.adidas.itinerarymanager.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Document
public class Itinerary {

    @Id
    private String id;

    @NotBlank(message = "itinerary.city.notBlank")
    private String city;

    @NotBlank(message = "itinerary.destinyCity.notBlank")
    private String destinyCity;

    @NotBlank(message = "itinerary.departureTime.notNull")
    @Pattern(regexp = "^\\d{2}:\\d{2}", message = "itinerary.departureTime.invalidPattern")
    private String departureTime;

    @NotBlank(message = "itinerary.arrivalTime.notNull")
    @Pattern(regexp = "^\\d{2}:\\d{2}", message = "itinerary.arrivalTime.invalidPattern")
    private String arrivalTime;
}
