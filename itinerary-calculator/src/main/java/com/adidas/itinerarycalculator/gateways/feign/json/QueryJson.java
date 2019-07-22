package com.adidas.itinerarycalculator.gateways.feign.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QueryJson {

    private String city;
}
