package com.codecool.solarwatchmvp.repository;

import com.codecool.solarwatchmvp.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
    Optional<City> findByNameIgnoreCase(String name);
    City save(City entity);
    void deleteByName(String name);
    List<City> findCitiesByNameContainingIgnoreCase(String name);
}
