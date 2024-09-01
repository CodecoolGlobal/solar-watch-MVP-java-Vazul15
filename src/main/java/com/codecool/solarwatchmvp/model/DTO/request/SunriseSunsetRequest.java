package com.codecool.solarwatchmvp.model.DTO.request;

import com.codecool.solarwatchmvp.model.entity.City;

import java.time.Instant;
import java.time.LocalDate;

public record SunriseSunsetRequest(Instant sunrise, Instant sunset, LocalDate date, String cityName) {
}
