package com.codecool.solarwatchmvp.model.DTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CityReport(String name, double lat, double lon, String state, String country) {
}
