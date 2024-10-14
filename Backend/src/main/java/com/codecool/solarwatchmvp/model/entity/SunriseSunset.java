package com.codecool.solarwatchmvp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"city_id", "date"})
)
public class SunriseSunset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private Instant sunrise;
    private Instant sunset;

    @ManyToOne
    private City city;

    public SunriseSunset(LocalDate date, Instant sunrise, Instant sunset, City city) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.city = city;
    }
}
