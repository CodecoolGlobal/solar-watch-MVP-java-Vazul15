package com.codecool.solarwatchmvp.repository;


import com.codecool.solarwatchmvp.model.SunriseSunset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface SunriseSunsetRepository extends JpaRepository<SunriseSunset, Long> {
    Optional<SunriseSunset> findByDateAndCityName(LocalDate date, String cityName);
    SunriseSunset save(SunriseSunset entity);
}
