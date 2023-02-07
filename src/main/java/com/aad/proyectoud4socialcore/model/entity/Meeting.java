package com.aad.proyectoud4socialcore.model.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int maxRadiusMeters;
    @OneToMany
    private List<SocialUser> attendants;

}
