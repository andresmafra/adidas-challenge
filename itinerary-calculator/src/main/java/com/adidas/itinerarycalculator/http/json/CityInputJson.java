package com.adidas.itinerarycalculator.http.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityInputJson {

    @NotBlank
    @ApiModelProperty(required = true, example = "São Paulo")
    private String from;

    @NotBlank
    @ApiModelProperty(required = true, example = "Rio de Janeiro")
    private String to;

    @ApiModelProperty(required = true, example = "CONNECTIONS", allowableValues = "CONNECTIONS, TIME")
    private String mode;
}
