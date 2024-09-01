package com.codecool.solarwatchmvp.controller;

import com.codecool.solarwatchmvp.exception.CityNotFoundException;
import com.codecool.solarwatchmvp.model.DTO.CityReport;
import com.codecool.solarwatchmvp.model.DTO.response.CityResponse;
import com.codecool.solarwatchmvp.model.DTO.response.CitySunriseSunset;
import com.codecool.solarwatchmvp.model.DTO.response.SunriseSunsetResponse;
import com.codecool.solarwatchmvp.model.entity.City;
import com.codecool.solarwatchmvp.model.entity.SunriseSunset;
import com.codecool.solarwatchmvp.service.OpenCityService;
import com.codecool.solarwatchmvp.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WeatherForecastController {
    private final OpenWeatherService openWeatherService;
    private final OpenCityService openCityService;

    @Autowired
    public WeatherForecastController(OpenWeatherService openWeatherService, OpenCityService openCityService) {
        this.openWeatherService = openWeatherService;
        this.openCityService = openCityService;
    }

    @GetMapping("/current")
    public SunriseSunsetResponse getCurrent(@RequestParam String cityName,
                                            @RequestParam String countryCode,
                                            @RequestParam(required = false) String stateCode,
                                            @RequestParam(required = false) LocalDate date) {
        CityReport cityReport = openCityService.getCityReport(cityName, stateCode, countryCode);
        return openWeatherService.getDemandedWeatherReport(cityReport, date);
    }


    @GetMapping("/cityPosition")
    public CityReport getWeatherForecast(@RequestParam(defaultValue = "Budapest") String cityName,
                                         @RequestParam String countryCode,
                                         @RequestParam(required = false) String stateCode,
                                         @RequestParam(required = false) LocalDate date
    ) {
        CityReport cityReport = openCityService.getCityReport(cityName, stateCode, countryCode);
        return cityReport;
    }

    @GetMapping("/search")
    public List<CitySunriseSunset> searchCitiesWithSunriseSunset(@RequestParam String query,
                                                            @RequestParam(required = false) LocalDate date) {
        List<CityReport> cities = openCityService.findCitiesByNameContainingIgnoreCase(query);

        return cities.stream().map(city -> {
            LocalDate targetDate = (date != null) ? date : LocalDate.now();
            SunriseSunsetResponse sunriseSunset = openWeatherService.getDemandedWeatherReport(city, targetDate);

            return new CitySunriseSunset(city.name(), sunriseSunset.results().sunrise(), sunriseSunset.results().sunset());
        }).toList();
    }


    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFoundException(CityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
