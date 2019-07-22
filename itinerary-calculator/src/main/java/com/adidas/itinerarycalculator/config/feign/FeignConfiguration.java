package com.adidas.itinerarycalculator.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.adidas.itinerarycalculator.gateways.feign"})
public class FeignConfiguration {

}