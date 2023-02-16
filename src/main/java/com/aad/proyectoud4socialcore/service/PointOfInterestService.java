package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.PlaceTypeAlreadyRequestedException;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.Residence;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.enums.SocialPlaceType;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PhotoRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointOfInterestService {

    private final int DEFAULT_RADIUS = 50_000;
    private final GeoApiContext geoContext;

    public PointOfInterestService(GeoApiContext geoContext) {
        this.geoContext = geoContext;
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

            byte[] photo = new PhotoRequest(geoContext).photoReference(place.photos[0].photoReference).maxWidth(1920).await().imageData;

            String[] placeTypes = place.types;

            placeTypes = Arrays.stream(placeTypes).filter(p -> {

                for(int i = 0; i < SocialPlaceType.values().length; i++ ){

                    if(SocialPlaceType.values()[i].name().equals(p)) {
                        return true;
                    }

                }

                return false;
            }).toArray(String[]::new);

            pointsOfInterest.add(new PointOfInterest(
                    place.name,
                    place.formattedAddress,
                    place.geometry.location,
                    place.openingHours,
                    place.businessStatus,
                    Arrays.stream(placeTypes).map(SocialPlaceType::valueOf).collect(Collectors.toSet()),
                    place.rating
            ));

        }

        return pointsOfInterest;
    }

    public PointOfInterest[] findClosePoiToUsers(SocialUser[] users, SocialPlaceType type) {

        ArrayList<PointOfInterest> commonPointsOfInterest = new ArrayList<>();
        ArrayList<PointOfInterest> allPoints = new ArrayList<>();
        ArrayList<PointOfInterest> out = new ArrayList<>();

        double centroidX = 0;
        double centroidY = 0;

        for (SocialUser user: users) {

            centroidX += user.getResidence().getCoordinates().lat / users.length;
            centroidY += user.getResidence().getCoordinates().lng / users.length;

            try {

                getNearbyPointsOfInterestForUser(user, type).forEach(el ->  {

                    if(allPoints.contains(el)) {

                        commonPointsOfInterest.add(el);

                    } else {

                        allPoints.add(el);

                    }

                });

            } catch (IOException | InterruptedException | ApiException ex) {

            }

        }

        LatLng centroid = new LatLng(centroidX, centroidY);

        for(PointOfInterest poi : commonPointsOfInterest) {

            if(poi.calculateDistanceToPoint(centroid) < 50_000 ) {

                out.add(poi);

            }

        }

        return out.toArray(PointOfInterest[]::new);
    }

}
