    import {useNavigate, useSearchParams} from "react-router-dom";
    import React, {useEffect, useState} from "react";
    import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest";
    import {PointOfInterestEndpoint} from "Frontend/generated/endpoints";
    import SocialAppBar from "Frontend/components/SocialAppBar";
    import Box from "@mui/material/Box";
    import Container from "@mui/material/Container";
    import Placeholder from "Frontend/components/placeholder/Placeholder";
    import {Card, ListItem, ListItemSecondaryAction, ListItemText} from "@mui/material";
    import Grid from "@mui/material/Grid";
    import Typography from "@mui/material/Typography";
    import {GoogleMap, Marker, useLoadScript} from "@react-google-maps/api";


    export default function PoiView() {

        const navigate = useNavigate();

        const [searchParams, setSearchParams] = useSearchParams();
        const [point, setPoint] = useState<PointOfInterest>();

        const {isLoaded} = useLoadScript({
            googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
        })

        const loadPointFromId = (pointId: number) => {
            return new Promise<PointOfInterest>((resolve, reject) => {

                try {

                    return resolve(PointOfInterestEndpoint.getPoiFromId(pointId));

                } catch (_) {

                    return reject(new Error("Point not found"));
                }
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
            <Box style={{
                backgroundImage: "url(https://www.eea.europa.eu/highlights/eight-facts-about-europe2019s-forest-ecosystems/image_print)",
                backgroundPosition: 'center',
                backgroundSize: 'cover',
                backgroundRepeat: 'space',
                height: "100%"
            }}>

                <SocialAppBar/>

                {point == null &&

                    <Placeholder />

                }

                {point != null &&

                    <Container>

                        <Card elevation={3}>

                            <Grid container spacing={12} padding={"20px"}>

                                <Grid item xs={12} md={12}>

                                    {/*TODO: poner imagen*/}

                                    <Typography variant="h2" textAlign="center">{point.name}</Typography>

                                </Grid>

                                <Grid item xs={12} md={6}>

                                    {isLoaded &&

                                        <GoogleMap options={{disableDefaultUI: true, zoomControl: true, gestureHandling: "greedy"}} mapContainerClassName="map-container" zoom={12} center={{lat: point.coordinates.lat, lng: point.coordinates.lng}}>

                                            <Marker label={point.name} position={point.coordinates}></Marker>

                                        </GoogleMap>

                                    }

                                    <ListItem>
                                        <ListItemText primary="Coordinates" secondary={point.coordinates.lat.toString() + ", " + point.coordinates.lng.toString()}></ListItemText>
                                    </ListItem>

                                </Grid>

                            </Grid>

                        </Card>

                    </Container>

                }

            </Box>
        );

    }