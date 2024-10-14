package com.codecool.solarwatchmvp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    private double longitude;
    private double latitude;
    private String country;
    private String state;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SunriseSunset> sunriseSunsets = new ArrayList<>();

    public City(String name, double longitude, double latitude, String country, String state) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.state = state;
    }
}
