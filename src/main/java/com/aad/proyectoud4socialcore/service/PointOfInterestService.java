package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.PlaceTypeAlreadyRequestedException;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.Residence;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.enums.SocialPlaceType;
import com.aad.proyectoud4socialcore.model.repository.PointOfInterestRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PhotoRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.hibernate.service.NullServiceException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointOfInterestService {

    private final int DEFAULT_RADIUS = 5000;
    private final int PHOTO_WIDTH = 400;
    private final GeoApiContext geoContext;
    private final PointOfInterestRepository pointOfInterestRepository;

    public PointOfInterestService(GeoApiContext geoContext, PointOfInterestRepository pointOfInterestRepository) {
        this.geoContext = geoContext;
        this.pointOfInterestRepository = pointOfInterestRepository;
    }

    public LatLng calculateCentroid(SocialUser[] users) {

        double centroidX = 0;
        double centroidY = 0;

        for(SocialUser user : users) {

            centroidX += user.getResidence().getCoordinates().lat / users.length;
            centroidY += user.getResidence().getCoordinates().lng / users.length;

        }

        return new LatLng(centroidX, centroidY);
    }


    public List<PointOfInterest> getNearbyPointsOfInterestForUser(SocialUser user, SocialPlaceType type) throws IOException, InterruptedException, ApiException {

        Residence userResidence = user.getResidence();
        List<PointOfInterest> points;

        if (userResidence.getRequestedTypes().contains(type))
            return user.getResidence().getNearbyPointsOfInterest().stream().filter(pointOfInterest -> pointOfInterest.getTypes().contains(type)).collect(Collectors.toList());

        userResidence.getRequestedTypes().add(type);

        points = getNearbyPointsOfInterest(userResidence.getCoordinates(), type);

        userResidence.getNearbyPointsOfInterest().addAll(points);

        return points;

    }


    private List<PointOfInterest> getNearbyPointsOfInterest(LatLng location, SocialPlaceType type) throws IOException, InterruptedException, ApiException {

        List<PointOfInterest> pointsOfInterest = new ArrayList<>();
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest(geoContext);
        nearbySearchRequest
                .location(location)
                .radius(DEFAULT_RADIUS)
                .type(type.getType());

        PlacesSearchResponse response = nearbySearchRequest.await();

        for (PlacesSearchResult place : response.results) {

            if (place.permanentlyClosed)
                continue;

            byte[] photo;
            try {
                photo = new PhotoRequest(geoContext)
                        .photoReference(place.photos[0].photoReference)
                        .maxWidth(PHOTO_WIDTH)
                        .await().imageData;
            } catch (NullPointerException e) {
                photo = new byte[0];
            }

            String[] placeTypes = place.types;

            placeTypes = Arrays.stream(placeTypes).filter(s -> {

                for(int i = 0; i < SocialPlaceType.values().length; i++ ) {

                    if(SocialPlaceType.values()[i].name().equals(s) ) {
                        return true;
                    }

                }

                return false;
            }).toArray(String[]::new);

            pointsOfInterest.add(new PointOfInterest(
                    place.name,
                    place.formattedAddress == null ? "No address" : place.formattedAddress,
                    place.geometry.location,
                    place.openingHours == null ? new OpeningHours() : place.openingHours,
                    place.businessStatus,
                    Arrays.stream(placeTypes).map(SocialPlaceType::valueOf).collect(Collectors.toSet()),
                    photo,
                    place.rating
            ));

        }



        return pointOfInterestRepository.saveAll(pointsOfInterest);
    }

    public PointOfInterest[] findClosePoiToUsers(SocialUser[] users, SocialPlaceType type) {

        ArrayList<PointOfInterest> points = new ArrayList<>();
        ArrayList<PointOfInterest> out = new ArrayList<>();

        double centroidX = 0;
        double centroidY = 0;

        for (SocialUser user: users) {

            centroidX += user.getResidence().getCoordinates().lat / users.length;
            centroidY += user.getResidence().getCoordinates().lng / users.length;

        }

        LatLng centroid = new LatLng(centroidX, centroidY);


        try {

            out = new ArrayList<>(getNearbyPointsOfInterest(centroid, type));

        } catch (Exception ex ) {

            ex.printStackTrace();

        }

        return out.toArray(PointOfInterest[]::new);
    }

}
