package com.adidas.itinerarycalculator.gateways.feign;

import com.adidas.itinerarycalculator.domains.Itinerary;
import com.adidas.itinerarycalculator.gateways.feign.fallback.ItineraryManagerFeignFallback;
import com.adidas.itinerarycalculator.gateways.feign.json.QueryJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "${feign.itineraryManager.name}", url = "${feign.itineraryManager.url}",
        fallbackFactory = ItineraryManagerFeignFallback.class, decode404 = true)
public interface ItineraryManagerFeign {

    @RequestMapping(method = POST,
            value = "/v1/itinerary/query/city",
            produces = APPLICATION_JSON_UTF8_VALUE,
            consumes = APPLICATION_JSON_UTF8_VALUE)
    Collection<Itinerary> queryByCity(@RequestBody QueryJson queryJson);
}
