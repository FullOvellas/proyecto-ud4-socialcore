package com.aad.proyectoud4socialcore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.PhotoRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "points_of_interest")
public class PointOfInterest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NonNull private String name;
    @NonNull private String formattedAddress;
    @NonNull private LatLng coordinates;
    @NonNull private OpeningHours openingHours;
    @NonNull private String businessStatus;

    @ElementCollection
    @NonNull private List<String> types;
    private byte @NonNull[] imageData;

    @NonNull private Float rating;
    @OneToMany(mappedBy = "pointOfInterest")
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany(mappedBy = "pointsOfInterest")
    private List<Meeting> meetings;

}
