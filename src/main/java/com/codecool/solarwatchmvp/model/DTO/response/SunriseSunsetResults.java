package com.codecool.solarwatchmvp.model.DTO.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;


@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetResults(Instant sunrise, Instant sunset) {

}
