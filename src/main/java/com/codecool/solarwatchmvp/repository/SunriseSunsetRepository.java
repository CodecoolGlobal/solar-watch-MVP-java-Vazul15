package com.codecool.solarwatchmvp.repository;


import com.codecool.solarwatchmvp.model.entity.SunriseSunset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunriseSunsetRepository extends JpaRepository<SunriseSunset, Long> {
    Optional<SunriseSunset> findByDateAndCityName(LocalDate date, String cityName);
    SunriseSunset save(SunriseSunset entity);
    boolean deleteByDateAndCityName(LocalDate date, String cityName);
}
