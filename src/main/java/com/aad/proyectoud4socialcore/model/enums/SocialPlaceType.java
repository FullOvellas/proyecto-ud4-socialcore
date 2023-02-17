package com.aad.proyectoud4socialcore.model.enums;

import com.google.maps.model.PlaceType;

public enum SocialPlaceType {

    CAFE(PlaceType.CAFE),
    PARK(PlaceType.PARK),
    FLORIST(PlaceType.FLORIST),
    NIGHT_CLUB(PlaceType.NIGHT_CLUB),
    RESTAURANT(PlaceType.RESTAURANT);

    private final PlaceType type;

    SocialPlaceType(PlaceType type) {
        this.type = type;
    }

    public PlaceType getType() {
        return type;
    }

}
