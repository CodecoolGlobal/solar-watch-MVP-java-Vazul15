package com.codecool.solarwatchmvp.service;

import com.codecool.solarwatchmvp.model.DTO.CityReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenCityServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenCityService openCityService;

//    @Test
//    void getCityReport_ValidCity_ReturnsCityReport() {
//        CityReport[] mockResponse = {new CityReport("Budapest", 47.4979, 19.0402)};
//        when(restTemplate.getForObject(anyString(), eq(CityReport[].class))).thenReturn(mockResponse);
//
//        CityReport cityReport = openCityService.getCityReport("Budapest", "", "HU");
//
//        assertEquals("Budapest", cityReport.name());
//        assertEquals(47.4979, cityReport.lat());
//        assertEquals(19.0402, cityReport.lon());
//    }
}