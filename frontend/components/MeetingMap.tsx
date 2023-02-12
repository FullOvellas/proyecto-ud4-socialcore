import {GoogleMap, useLoadScript, Marker} from "@react-google-maps/api";
import CircularProgress from "@mui/material/CircularProgress";
import {useMemo, useState} from "react";
import Residence from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Residence"
import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest";

export default function MeetingMap() {

    const { isLoaded } = useLoadScript({
        // @ts-ignore
        googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
    });

    if (!isLoaded)
        return <CircularProgress />;

    return <Map />;

}

function Map() {

    return (
        <>
            <GoogleMap
                zoom={9}
                center={{lat: 44, lng: -80}}
                mapContainerClassName="map-container"
            >
                <Marker position={{lat: 44, lng: -80}} />
            </GoogleMap>
        </>
    );

}
