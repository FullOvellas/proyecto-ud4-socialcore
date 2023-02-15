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
    private SocialUser user;

    private Float rating;

    @ManyToOne
    @JsonIgnore
    private PointOfInterest pointOfInterest;

    public Comment() {

        rating = 0f;

    }

}
