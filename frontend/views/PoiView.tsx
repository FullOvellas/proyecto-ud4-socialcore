import {useNavigate, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest";


export default function PoiView() {

    const navigate = useNavigate();

    const [searchParams, setSearchParams] = useSearchParams();
    const [point, setPoint] = useState<PointOfInterest>();

    const loadPointFromId = (pointId: number) => {
        return new Promise<PointOfInterest>((resolve, reject) => {

            // TODO: implementar

        })
    }

    useEffect(() => {

        if(searchParams.get("point_id") == null) {
            // TODO: enviar a página "acceso restringido"
            navigate("/");
        }

        let pointId = parseInt(searchParams.get("point_id")!);

        loadPointFromId(pointId)
            .then(setPoint)
            .catch(_ => navigate("/"))

    });

    return (
        <>

        </>
    );

}