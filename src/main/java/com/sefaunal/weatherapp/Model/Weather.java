package com.sefaunal.weatherapp.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long weatherID;

    private String weatherCity;
    private String weatherCountry;
    private String weatherStatus;
    private String weatherIconURL;
    private float weatherTemp;
    private int weatherWind;
    private int weatherHumidity;
    private int weatherPressure;

    private Timestamp weatherDate;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
}
