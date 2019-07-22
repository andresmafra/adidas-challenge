package com.adidas.itinerarycalculator.gateways.feign.fallback;

import com.adidas.itinerarycalculator.domains.Itinerary;
import com.adidas.itinerarycalculator.gateways.feign.ItineraryManagerFeign;
import com.adidas.itinerarycalculator.gateways.feign.json.QueryJson;
import com.google.common.collect.Lists;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Slf4j
@Component
public class ItineraryManagerFeignFallback implements FallbackFactory<ItineraryManagerFeign> {

    @Override
    public ItineraryManagerFeign create(final Throwable throwable) {
        return new ItineraryManagerFeign() {
            @Override
            public Collection<Itinerary> queryByCity(@RequestBody final QueryJson queryJson) {
                log.error("Error trying to reach ItineraryManager api: {} - {}", throwable.getMessage(), throwable.getCause());
                log.error("Error: {}", throwable.getStackTrace());
                return Lists.newArrayList();
            }
        };
    }
}
