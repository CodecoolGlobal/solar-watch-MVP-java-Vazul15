package com.codecool.solarwatchmvp.controller;

import com.codecool.solarwatchmvp.model.DTO.CityReport;
import com.codecool.solarwatchmvp.model.DTO.request.SunriseSunsetRequest;
import com.codecool.solarwatchmvp.model.DTO.response.CityResponse;
import com.codecool.solarwatchmvp.model.DTO.response.SunriseSunsetResults;
import com.codecool.solarwatchmvp.model.DTO.response.UserResponse;
import com.codecool.solarwatchmvp.model.entity.City;
import com.codecool.solarwatchmvp.model.entity.Role;
import com.codecool.solarwatchmvp.model.entity.UserEntity;
import com.codecool.solarwatchmvp.model.payload.UserRequest;
import com.codecool.solarwatchmvp.repository.UserRepository;
import com.codecool.solarwatchmvp.security.jwt.JwtUtils;
import com.codecool.solarwatchmvp.service.OpenCityService;
import com.codecool.solarwatchmvp.service.OpenWeatherService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final OpenWeatherService openWeatherService;
    private final OpenCityService openCityService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AdminController(OpenWeatherService openWeatherService, OpenCityService openCityService, UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.openWeatherService = openWeatherService;
        this.openCityService = openCityService;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;

    }

    @PostMapping("/registerAdmin")
    public UserResponse editCity(@RequestBody UserRequest signUpRequest) {
        UserEntity user = new UserEntity(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                Set.of(Role.ROLE_ADMIN));
        UserEntity savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId());
    }

    @PutMapping("/cities")
    @PreAuthorize("hasRole('ADMIN')")
    public CityReport editCity(@RequestParam String cityName, @RequestBody City updatedCity) {
        return openCityService.updateCity(cityName, updatedCity);
    }

    @DeleteMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCity(@RequestParam String cityName) {
        openCityService.deleteCityByName(cityName);
    }

    @PostMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    public CityResponse createCity(@RequestBody City newCity) {
        return openCityService.createCity(newCity);
    }

    @PostMapping("/sunrisesunset")
    @PreAuthorize("hasRole('ADMIN')")
    public SunriseSunsetResults createSunriseSunset(@RequestParam String cityName,
                                                    @RequestParam String countryCode,
                                                    @RequestParam(required = false) String stateCode, @RequestBody SunriseSunsetRequest sunriseSunsetRequest) {
        CityReport city = openCityService.getCityReport(cityName, countryCode, stateCode);
        return openWeatherService.createSunriseSunset(city, sunriseSunsetRequest);
    }

    @PutMapping("/sunrisesunset")
    @PreAuthorize("hasRole('ADMIN')")
    public SunriseSunsetResults updateSunriseSunset(@RequestParam LocalDate date, @RequestParam String
            cityName, @RequestBody SunriseSunsetRequest updatedSunriseSunset) {
        return openWeatherService.updateWeatherReport(date, cityName, updatedSunriseSunset);
    }

    @DeleteMapping("/sunrisesunset")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteSunriseSunset(@RequestParam LocalDate date, @RequestParam String cityName) {
        openWeatherService.deleteSunRiseSunSet(date, cityName);
        return true;
    }

}
