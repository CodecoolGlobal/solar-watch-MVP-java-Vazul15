package com.codecool.solarwatchmvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetResponse(SunriseSunsetResults results) {}
