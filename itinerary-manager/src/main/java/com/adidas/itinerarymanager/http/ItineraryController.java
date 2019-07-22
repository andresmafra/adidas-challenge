package com.adidas.itinerarymanager.http;

import com.adidas.itinerarymanager.domains.Itinerary;
import com.adidas.itinerarymanager.http.json.QueryJson;
import com.adidas.itinerarymanager.usecases.CrudItinerary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/itinerary")
@Api(tags = "Itinerary management",
        description = "Rest api for managing city itineraries",
        produces = APPLICATION_JSON_VALUE)
public class ItineraryController {

    private final CrudItinerary crudItinerary;

    @ApiOperation(value = "List all itineraries")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All registered itineraries", response = Itinerary.class,
                    responseContainer = "List"),
    })
    @GetMapping
    @ResponseStatus(OK)
    public Collection<Itinerary> list() {
        log.debug("Finding all itineraries");
        return crudItinerary.list();
    }

    @ApiOperation(value = "Find by city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All registered itineraries with city informed", response = Itinerary.class,
                    responseContainer = "List"),
    })
    @PostMapping("/query/city")
    @ResponseStatus(OK)
    public Collection<Itinerary> queryByCity(@RequestBody @Valid final QueryJson queryJson) {
        log.debug("Querying all itineraries by city :{}", queryJson.getCity());
        return crudItinerary.findAllByCity(queryJson.getCity());
    }

    @ApiOperation(value = "Create a new itinerary")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Itinerary created", response = Itinerary.class),
            @ApiResponse(code = 400, message = "Invalid itinerary"),
            @ApiResponse(code = 422, message = "Itinerary already exists")
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public Itinerary create(@Valid @RequestBody final Itinerary itinerary) {
        log.debug("Creating new itinerary: {}", itinerary);
        return crudItinerary.create(itinerary);
    }

    @ApiOperation(value = "Update an existing itinerary")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Itinerary updated", response = Itinerary.class),
            @ApiResponse(code = 400, message = "Invalid itinerary"),
            @ApiResponse(code = 422, message = "Itinerary already exists")
    })
    @PutMapping(value = "/{id}")
    @ResponseStatus(OK)
    public Itinerary update(@NotNull @PathVariable final String id,
                            @Valid @RequestBody final Itinerary itinerary) {
        log.debug("Updating itinerary: {} for id: {}", itinerary, id);
        return crudItinerary.update(id, itinerary);
    }

    @ApiOperation(value = "Remove an existing itinerary")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Itinerary removed", response = Itinerary.class),
            @ApiResponse(code = 404, message = "Itinerary not found")
    })
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void remove(@NotNull @PathVariable final String id) {
        log.debug("Removing itinerary for id: {}", id);
        crudItinerary.delete(id);
    }
}
