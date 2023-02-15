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
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "points_of_interest")
public class PointOfInterest {

    private static final double EARTH_RADIUS = 6_371_000;
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

    @JsonIgnore
    private byte @NonNull[] imageData;

    @NonNull private Float rating;
    @OneToMany(mappedBy = "pointOfInterest")
    @JsonIgnore
    private List<Comment> comments;


    public PointOfInterest() {
        this.comments = new ArrayList<>();
        this.types = new ArrayList<>();
    }

    // Poor man's distance matrix TODO implementar distance matrix se tiveramos cartos
    public double calculateDistanceToPoint(LatLng originCoordinates) {

        double originLongRadians = Math.toRadians(originCoordinates.lng);
        double originLatRadians = Math.toRadians(originCoordinates.lat);
        double pointOfInterestLongRadians = Math.toRadians(coordinates.lng);
        double pointOfInterestLatRadians = Math.toRadians(coordinates.lat);

        // Spherical law of cosines
        return Math.acos(Math.sin(originLatRadians)
                * Math.sin(pointOfInterestLatRadians)
                + Math.cos(originLatRadians)
                * Math.cos(pointOfInterestLatRadians)
                * Math.cos(pointOfInterestLongRadians - originLongRadians))
                * EARTH_RADIUS;

    }

    @Override
    public boolean equals(Object o) {

        if(!(o instanceof PointOfInterest)) {
            return false;
        }

        PointOfInterest p = (PointOfInterest) o;

        return p.getId().equals(getId());
    }

}
