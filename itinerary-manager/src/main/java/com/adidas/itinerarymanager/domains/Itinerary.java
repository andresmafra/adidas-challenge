package com.adidas.itinerarymanager.domains;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(required = true, example = "São Paulo")
    private String city;

    @NotBlank(message = "itinerary.destinyCity.notBlank")
    @ApiModelProperty(required = true, example = "Rio de Janeiro")
    private String destinyCity;

    @NotBlank(message = "itinerary.departureTime.notNull")
    @Pattern(regexp = "^\\d{2}:\\d{2}", message = "itinerary.departureTime.invalidPattern")
    @ApiModelProperty(required = true, example = "08:00")
    private String departureTime;

    @NotBlank(message = "itinerary.arrivalTime.notNull")
    @Pattern(regexp = "^\\d{2}:\\d{2}", message = "itinerary.arrivalTime.invalidPattern")
    @ApiModelProperty(required = true, example = "15:00")
    private String arrivalTime;
}
