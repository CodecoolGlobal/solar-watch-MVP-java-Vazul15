package com.codecool.solarwatchmvp.service;

import com.codecool.solarwatchmvp.model.*;
import com.codecool.solarwatchmvp.repository.CityRepository;
import com.codecool.solarwatchmvp.repository.SunriseSunsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class OpenWeatherService {
    private final RestTemplate restTemplate;
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private final CityRepository cityRepository;

    @Autowired
    public OpenWeatherService(RestTemplate restTemplate, SunriseSunsetRepository sunriseSunsetRepository, CityRepository cityRepository) {
        this.restTemplate = restTemplate;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
        this.cityRepository = cityRepository;
    }
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherService.class);

    private Optional<SunriseSunset> getSunriseSunsetByDateAndCity(LocalDate date, String cityName) {
        return sunriseSunsetRepository.findByDateAndCityName(date, cityName);
    }

    private String buildString(LocalDate localDate) {
        if (localDate != null) {
            return localDate.toString();
        } else {
            return "today";
        }
    }

    public SunriseSunsetResponse getDemandedWeatherReport(CityReport city, LocalDate date) {
        Optional<City> foundCity = cityRepository.findByName(city.name());

        return getSunriseSunsetByDateAndCity(date, city.name())
                .map(element -> new SunriseSunsetResponse(
                        new SunriseSunsetResults(
                                element.getSunrise(),
                                element.getSunset()
                        )
                ))
                .orElseGet(() -> {

                    String formattedDate = buildString(date);
                    String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s&formatted=0", city.lat(), city.lon(), formattedDate);

                    SunriseSunsetResponse response = restTemplate.getForObject(url, SunriseSunsetResponse.class);
                    logger.info("Response from Sunrise-Sunset API: {}", response);

                    if (response != null) {
                        SunriseSunset newSunriseSunset = new SunriseSunset();

                        newSunriseSunset.setCity(foundCity.orElseThrow(() -> new RuntimeException("City not found")));
                        newSunriseSunset.setDate(date);
                        newSunriseSunset.setSunrise(response.results().sunrise());
                        newSunriseSunset.setSunset(response.results().sunset());
                        sunriseSunsetRepository.save(newSunriseSunset);

                        return response;
                    } else {
                        throw new RuntimeException("Failed to fetch sunrise and sunset times");
                    }
                });
    }
}