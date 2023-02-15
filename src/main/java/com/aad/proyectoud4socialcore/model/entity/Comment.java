package com.aad.proyectoud4socialcore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String text;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "point_of_interest_id")
    private PointOfInterest pointOfInterest;
}
