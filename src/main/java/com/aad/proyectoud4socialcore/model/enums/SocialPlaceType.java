package com.aad.proyectoud4socialcore.model.enums;

import com.google.maps.model.PlaceType;

public enum SocialPlaceType {

    CAFE(PlaceType.CAFE),
    SCHOOL(PlaceType.SCHOOL);

    private final PlaceType type;

    SocialPlaceType(PlaceType type) {
        this.type = type;
    }

    public PlaceType getType() {
        return type;
    }

}
