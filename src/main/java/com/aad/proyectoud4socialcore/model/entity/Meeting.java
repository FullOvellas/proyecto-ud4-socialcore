package com.aad.proyectoud4socialcore.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.sql.Timestamp;
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

    private String name;

    @OneToOne(cascade = CascadeType.MERGE)
    @NonNull
    private PointOfInterest destination;
    private int maxRadiusMeters;

    @ManyToMany
    @JoinTable(
            name = "meetings_attendants",
            joinColumns = {
                    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "attendant_id", referencedColumnName = "id")
            }
    )
    private List<SocialUser> attendants;

    @JsonGetter("plannedTime")
    public String plannedTimeString() {

        System.out.println("YESSS");

        return plannedTime.toString();
    }

    private Timestamp plannedTime;

    public Meeting() {
        this.attendants = new ArrayList<>();
    }

}
