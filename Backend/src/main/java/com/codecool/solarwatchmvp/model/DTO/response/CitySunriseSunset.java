package com.codecool.solarwatchmvp.model.DTO.response;

import java.time.Instant;
import java.time.LocalDate;

public record CitySunriseSunset(String name, Instant sunrise, Instant sunset, LocalDate date) {
}
