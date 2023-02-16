package com.aad.proyectoud4socialcore.model.entity;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LatLng coordinates;
    @ElementCollection
    @NonNull
    private Set<PlaceType> requestedTypes = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private List<PointOfInterest> nearbyPointsOfInterest = new ArrayList<>();

    public Residence(LatLng coordinates) {
        this.coordinates = coordinates;
    }

}
