package com.aad.proyectoud4socialcore.model.entity;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Set<PlaceType> requestedTypes;
    @OneToMany(fetch = FetchType.LAZY)
    private List<PointOfInterest> nearbyPointsOfInterest;

    public Residence(LatLng coordinates) {
        this.coordinates = coordinates;
    }

}
