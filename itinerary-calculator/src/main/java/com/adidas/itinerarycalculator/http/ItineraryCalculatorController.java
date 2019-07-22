package com.adidas.itinerarycalculator.http;

import com.adidas.itinerarycalculator.domains.CalculateMode;
import com.adidas.itinerarycalculator.domains.ItineraryOutput;
import com.adidas.itinerarycalculator.exceptions.InvalidCalculateMethod;
import com.adidas.itinerarycalculator.http.json.CityInputJson;
import com.adidas.itinerarycalculator.usecases.CalculateItinerary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/calculate")
@Api(tags = "Itinerary calculator",
        description = "Rest api for calculating city connections",
        produces = APPLICATION_JSON_VALUE)
public class ItineraryCalculatorController {

    private final CalculateItinerary calculateItinerary;

    @ApiOperation(value = "Calculates a itinerary by minimum connections or travel time")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Itinerary found", response = ItineraryOutput.class),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Itinerary not found")
    })
    @PostMapping
    @ResponseStatus(OK)
    public ItineraryOutput calculate(@Valid @RequestBody final CityInputJson input) {
        log.debug("Calculating best itinerary based on mode: {}", input.getMode());
        final CalculateMode mode;
        try {
            mode = CalculateMode.valueOf(input.getMode());
        } catch (final Exception e) {
            log.error("Invalid calculate mode: {}", input.getMode());
            throw new InvalidCalculateMethod(String.format("Mode %s not allowed", input.getMode()));
        }
        return calculateItinerary.calculate(input.getFrom(), input.getTo(), mode);
    }
}
