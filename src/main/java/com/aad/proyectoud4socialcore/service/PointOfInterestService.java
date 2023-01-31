package com.aad.proyectoud4socialcore.service;

import com.google.maps.GeoApiContext;
import org.springframework.stereotype.Service;

@Service
public class PointOfInterestService {

    private GeoApiContext geoContext;

    public PointOfInterestService(GeoApiContext geoContext) {
        this.geoContext = geoContext;
    }

    

}
