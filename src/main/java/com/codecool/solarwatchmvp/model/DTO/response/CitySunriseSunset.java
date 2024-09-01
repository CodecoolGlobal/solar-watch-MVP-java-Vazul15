package com.codecool.solarwatchmvp.model.DTO.response;

import java.time.Instant;

public record CitySunriseSunset(String CityName, Instant sunrise, Instant sunset) {
}
