package com.codecool.solarwatchmvp.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;


@Entity
public class SunriseSunset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private Instant sunrise;
    private Instant sunset;

    @ManyToOne
    private City city;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getSunrise() {
        return sunrise;
    }

    public void setSunrise(Instant sunrise) {
        this.sunrise = sunrise;
    }

    public Instant getSunset() {
        return sunset;
    }

    public void setSunset(Instant sunset) {
        this.sunset = sunset;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
