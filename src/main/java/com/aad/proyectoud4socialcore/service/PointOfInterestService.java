package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.PlaceTypeAlreadyRequestedException;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.Residence;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
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

    private final int DEFAULT_RADIUS = 50_000;
    private final int PHOTO_WIDTH = 400;
    private final GeoApiContext geoContext;

    public PointOfInterestService(GeoApiContext geoContext) {
        this.geoContext = geoContext;
    }


    public List<PointOfInterest> getNearbyPointsOfInterestForUser(SocialUser user, String type) throws PlaceTypeAlreadyRequestedException, IOException, InterruptedException, ApiException {

        PlaceType placeType = PlaceType.valueOf(type);
        Residence userResidence = user.getResidence();

        if (userResidence.getRequestedTypes().contains(placeType))
            throw new PlaceTypeAlreadyRequestedException("This user has already requested nearby places of this type");

        userResidence.getRequestedTypes().add(placeType);
        return getNearbyPointsOfInterest(userResidence.getCoordinates(), placeType);

    }


    private List<PointOfInterest> getNearbyPointsOfInterest(LatLng location, PlaceType type) throws IOException, InterruptedException, ApiException {

        List<PointOfInterest> pointsOfInterest = new ArrayList<>();
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest(geoContext);
        nearbySearchRequest
                .location(location)
                .radius(DEFAULT_RADIUS)
                .type(type);

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

            ArrayList<String> placeTypes = new ArrayList<>();
            placeTypes.addAll(Arrays.asList(place.types));

            pointsOfInterest.add(new PointOfInterest(
                    place.name,
                    place.formattedAddress == null ? "No address" : place.formattedAddress,
                    place.geometry.location,
                    place.openingHours == null ? new OpeningHours() : place.openingHours,
                    place.businessStatus,
                    placeTypes,
                    photo,
                    place.rating
            ));

        }

        return pointsOfInterest;
    }

}
