package com.adidas.itinerarymanager.http.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class QueryJson {

    @NotBlank
    private String city;
}
