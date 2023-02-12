package com.aad.proyectoud4socialcore.model.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @OneToOne
    @NonNull
    private PointOfInterest destination;
    private int maxRadiusMeters;
    @OneToMany
    private List<SocialUser> attendants;

}
