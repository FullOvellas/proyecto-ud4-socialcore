package com.aad.proyectoud4socialcore.model.entity;

import javax.persistence.*;

import lombok.Data;
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

    @OneToMany
    private List<SocialUser> attendants;

    private DateTime plannedTime;

    @ManyToMany
    @JoinTable(name = "meetings_points",
        joinColumns = {
            @JoinColumn(name = "meeting_id",
                referencedColumnName = "id"
            )
        },
        inverseJoinColumns = {
            @JoinColumn(name = "point_id",
                referencedColumnName = "id"
            )
        }
    )
    private List<PointOfInterest> pointsOfInterest;

    public Meeting() {
        this.pointsOfInterest = new ArrayList<>();
    }

}
