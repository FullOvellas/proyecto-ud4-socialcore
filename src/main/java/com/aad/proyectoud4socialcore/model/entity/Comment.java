package com.aad.proyectoud4socialcore.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String text;

    @ManyToOne
    @JoinColumn(name = "point_of_interest_id")
    private PointOfInterest pointOfInterest;
}
