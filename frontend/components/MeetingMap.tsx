import {GoogleMap, useLoadScript, Marker} from "@react-google-maps/api";
import CircularProgress from "@mui/material/CircularProgress";
import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import {useState} from "react";

export default function MeetingMap({meeting}: {meeting: Meeting}, ) {

    const destinationLatLng = meeting.destination.coordinates;

    const { isLoaded } = useLoadScript({
        // @ts-ignore
        googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
    });

    if (!isLoaded)
        return <CircularProgress />;

    let attendants: Array<SocialUser> = [];

    meeting.attendants?.map(user => {
        if (user!) {
            attendants = [...attendants, user];
        }
    });

    return <Map lat={destinationLatLng.lat} lng={destinationLatLng.lng} attendants={attendants} />;

}

function Map({lat, lng, attendants}: {lat: number, lng: number, attendants: Array<SocialUser>}) {

    const residences: Array<{lat: number, lng: number}> = attendants.map(att => {
        return {lat: att.residence!.coordinates!.lat, lng: att.residence!.coordinates!.lng};
    });

    return (
        <>
            <GoogleMap
                zoom={12}
                center={{lat, lng}}
                mapContainerClassName="map-container"
            >
                <Marker position={{lat, lng}} />
                {residences.map((res, index) => <Marker label={attendants[index].fullName} key={index} position={res} />)}
            </GoogleMap>
        </>
    );

}
