package com.aad.proyectoud4socialcore.model.entity;

import com.google.maps.model.LatLng;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private List<String> requestedTypes;

}
