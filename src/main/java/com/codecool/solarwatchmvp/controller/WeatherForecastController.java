package com.codecool.solarwatchmvp.controller;

import com.codecool.solarwatchmvp.exception.CityNotFoundException;
import com.codecool.solarwatchmvp.model.CityReport;
import com.codecool.solarwatchmvp.model.SunriseSunsetResponse;
import com.codecool.solarwatchmvp.service.OpenCityService;
import com.codecool.solarwatchmvp.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
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

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFoundException(CityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
