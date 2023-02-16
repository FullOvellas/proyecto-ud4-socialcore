package com.aad.proyectoud4socialcore.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private PointOfInterest destination;
    private int maxRadiusMeters;

    @OneToMany
    private List<SocialUser> attendants;

    @JsonIgnore
    private DateTime plannedTime;

    public Meeting() {
        this.destination = new PointOfInterest();
        this.attendants = new ArrayList<>();
    }

}
