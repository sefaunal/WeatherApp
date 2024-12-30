package com.sefaunal.weatherapp.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    private String userName;

    private String userSurname;

    private String userPassword;

    @Column(columnDefinition = "VARCHAR(150) NOT NULL", unique = true)
    private String userMail;

    private String userRole;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime userCreationDate;

    private String userImageURL;

    @Column(columnDefinition = "TEXT")
    private String userBIO;

    @OneToMany(targetEntity = Weather.class, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Weather> weatherList;

}
