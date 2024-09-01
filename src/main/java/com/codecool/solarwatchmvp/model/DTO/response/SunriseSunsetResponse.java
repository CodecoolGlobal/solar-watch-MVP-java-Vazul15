package com.codecool.solarwatchmvp.model.DTO.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetResponse(SunriseSunsetResults results) {}
