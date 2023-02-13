import {GoogleMap, useLoadScript, Marker} from "@react-google-maps/api";
import CircularProgress from "@mui/material/CircularProgress";
import {useMemo, useState} from "react";
import Residence from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Residence"
import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest";
import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";
import LatLng = google.maps.LatLng;
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";

export default function MeetingMap({meeting}: {meeting: Meeting}) {

    const destinationLatLng = meeting.destination.coordinates;

    const { isLoaded } = useLoadScript({
        // @ts-ignore
        googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
    });

    if (!isLoaded)
        return <CircularProgress />;

    return <Map lat={destinationLatLng.lat} lng={destinationLatLng.lng} />;

}

function Map({lat, lng}: {lat: number, lng: number}, {attendants}: {attendants: Array<SocialUser>}) {

    return (
        <>
            <GoogleMap
                zoom={12}
                center={{lat, lng}}
                mapContainerClassName="map-container"
            >
                meetin
            </GoogleMap>
        </>
    );

}
