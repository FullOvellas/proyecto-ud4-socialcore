package com.aad.proyectoud4socialcore.model.entity;

import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.PhotoRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Data
@Entity
@Table(name = "points_of_interest")
public class PointOfInterest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String formattedAddress;
    @NotNull
    private LatLng coordinates;
    @NotNull
    private OpeningHours openingHours;
    @NotNull
    private String businessStatus;
    @NotNull
    @ElementCollection
    private List<String> types;
    @NotNull
    private byte[] imageData;
    @NotNull
    private float rating;
    @OneToMany(mappedBy = "pointOfInterest")
    private List<Comment> comments;

}
