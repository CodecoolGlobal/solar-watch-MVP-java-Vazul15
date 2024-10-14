package com.codecool.solarwatchmvp.service;

import com.codecool.solarwatchmvp.exception.CityNotFoundException;
import com.codecool.solarwatchmvp.model.DTO.response.CityResponse;
import com.codecool.solarwatchmvp.model.entity.City;
import com.codecool.solarwatchmvp.model.DTO.CityReport;
import com.codecool.solarwatchmvp.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OpenCityService {
    private static final String API_KEY = "b491ad43ba62f535b4ce4a9a96195518";
    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;

    @Autowired
    public OpenCityService(RestTemplate restTemplate, CityRepository cityRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(com.codecool.solarwatchmvp.service.OpenCityService.class);

    private Optional<City> getCityByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    private String buildUrl(String cityName, String stateCode, String countryCode) {
        if (stateCode != null && !stateCode.isEmpty()) {
            return String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s,%s&limit=1&appid=%s",
                    cityName, stateCode, countryCode, API_KEY);
        } else {
            return String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=1&appid=%s",
                    cityName, countryCode, API_KEY);
        }
    }

    public CityReport getCityReport(String cityName, String stateCode, String countryCode) {

        Optional<City> existingCity = cityRepository.findByNameIgnoreCase(cityName);
        logger.info("City name: {}, alreadyInDatabase: {}", cityName, existingCity.isPresent());
        if (existingCity.isPresent()) {
            logger.info("City already exists: {}", existingCity.get().getName());
            return new CityReport(existingCity.get().getName(), existingCity.get().getLatitude(), existingCity.get().getLongitude(), existingCity.get().getState(), existingCity.get().getCountry());
        } else {
            String url = buildUrl(cityName, stateCode, countryCode);

            logger.info("Requesting URL: {}", url);
            CityReport[] response = restTemplate.getForObject(url, CityReport[].class);

            if (response != null && response.length > 0 && response[0].name() != null && response[0].lat() != 0 && response[0].lon() != 0) {
                City city = new City();
                city.setName(response[0].name());
                city.setLatitude(response[0].lat());
                city.setLongitude(response[0].lon());
                city.setState(response[0].state());
                city.setCountry(response[0].country());

                logger.info("Saving city: {}, Latitude: {}, Longitude: {}", city.getName(), city.getLatitude(), city.getLongitude());
                cityRepository.save(city);

                return new CityReport(city.getName(), city.getLatitude(), city.getLongitude(), city.getState(), city.getCountry());
            } else {
                String errorMessage = "No valid city found for the given parameters: cityName=" + cityName + ", countryCode=" + countryCode;
                if (stateCode != null && !stateCode.isEmpty()) {
                    errorMessage += ", stateCode=" + stateCode;
                }
                logger.error(errorMessage);
                throw new CityNotFoundException(errorMessage);
            }
        }
    }

    public CityReport updateCity(String cityName, City updatedCity) {
        City existingCity = cityRepository.findByName(cityName)
                .orElseThrow(() -> new RuntimeException("City not found"));

        if (updatedCity.getName() != null) {
            existingCity.setName(updatedCity.getName());
        }
        if (updatedCity.getCountry() != null) {
            existingCity.setCountry(updatedCity.getCountry());
        }
        if (updatedCity.getState() != null) {
            existingCity.setState(updatedCity.getState());
        }
        if (updatedCity.getLatitude() != 0) {
            existingCity.setLatitude(updatedCity.getLatitude());
        }
        if (updatedCity.getLongitude() != 0) {
            existingCity.setLongitude(updatedCity.getLongitude());
        }

        cityRepository.save(existingCity);

        return new CityReport(existingCity.getName(),
                existingCity.getLatitude(), existingCity.getLongitude(),
                existingCity.getState(), existingCity.getCountry());
    }

    public void deleteCityByName(String cityName) {
        City foundCity = cityRepository.findByName(cityName).orElseThrow(() -> new CityNotFoundException(cityName));
        cityRepository.delete(foundCity);
    }

    public CityResponse createCity(City newCity) {
        cityRepository.save(newCity);
        return new CityResponse(newCity.getName());
    }

    public List<CityReport> findCitiesByNameContainingIgnoreCase(String name) {
        return cityRepository.findCitiesByNameContainingIgnoreCase(name)
                .stream()
                .map(city -> new CityReport(city.getName(), city.getLatitude(), city.getLongitude(), city.getState(), city.getCountry()))
                .toList();
    }
}
