package com.aad.proyectoud4socialcore.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @NonNull
    private PointOfInterest destination;
    private int maxRadiusMeters;

    @OneToMany
    private List<SocialUser> attendants;

    @JsonIgnore
    private Date plannedTime;

    public Meeting() {
        this.attendants = new ArrayList<>();
    }

}
