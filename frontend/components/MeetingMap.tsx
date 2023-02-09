import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";
require("https/googlemaps");
import {Children, cloneElement, isValidElement, useEffect, useRef, useState} from "react";


export default function MeetingMap(meeting: Meeting) {
    const ref = useRef<HTMLDivElement>(null);
    const [map, setMap] = useState<google.maps.Map>();

    useEffect(() => {
        if (ref.current && !map) {
            setMap(new window.google.maps.Map(ref.current, {}))
        }
    }, [ref, map])

    return (
        <>

        </>
    );

}
