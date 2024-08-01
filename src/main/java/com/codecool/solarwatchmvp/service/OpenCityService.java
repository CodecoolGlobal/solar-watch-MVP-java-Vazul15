package com.codecool.solarwatchmvp.service;

import com.codecool.solarwatchmvp.exception.CityNotFoundException;
import com.codecool.solarwatchmvp.model.City;
import com.codecool.solarwatchmvp.model.CityReport;
import com.codecool.solarwatchmvp.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        return getCityByName(cityName)
                .map(city -> new CityReport(city.getName(), city.getLatitude(), city.getLongitude(), city.getState(), city.getCountry()))
                .orElseGet(() -> {
                    String url = buildUrl(cityName, stateCode, countryCode);

                    logger.info("Requesting URL: {}", url);
                    CityReport[] response = restTemplate.getForObject(url, CityReport[].class);

                    if (response != null && response.length > 0) {
                        logger.info("Response from OpenWeather API for {}: {}", cityName, response[0]);

                        City city = new City();
                        city.setName(response[0].name());
                        city.setLatitude(response[0].lat());
                        city.setLongitude(response[0].lon());
                        city.setState(response[0].state());
                        city.setCountry(response[0].country());
                        cityRepository.save(city);

                        return response[0];
                    } else {
                        String errorMessage = "No city found for the given parameters: cityName=" + cityName + ", countryCode=" + countryCode;
                        if (stateCode != null && !stateCode.isEmpty()) {
                            errorMessage += ", stateCode=" + stateCode;
                        }
                        logger.error(errorMessage);
                        throw new CityNotFoundException(errorMessage);
                    }
                });
    }
}
