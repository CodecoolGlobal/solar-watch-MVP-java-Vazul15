package com.codecool.solarwatchmvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.time.LocalDateTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetResults(Instant sunrise, Instant sunset) {

}
