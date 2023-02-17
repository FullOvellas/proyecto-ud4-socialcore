import {Card, Box, Container, List, ListItemButton, Chip, Paper, ListItem, ListItemText} from "@mui/material";
import SocialAppBar from "Frontend/components/SocialAppBar";
import Grid from "@mui/material/Grid";
import React, {useEffect, useState} from "react";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest";
import Typography from "@mui/material/Typography";
import {GoogleMap, Marker, useLoadScript} from "@react-google-maps/api";
import CircularProgress from "@mui/material/CircularProgress";
import {MeetingEndpoint, PointOfInterestEndpoint, UserAuthEndpoint} from "Frontend/generated/endpoints";
import Button from "@mui/material/Button";
import {Info} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";
import SocialPlaceType from "Frontend/generated/com/aad/proyectoud4socialcore/model/enums/SocialPlaceType";
import Placeholder from "Frontend/components/placeholder/Placeholder";
import LatLng from "Frontend/generated/com/google/maps/model/LatLng";

export default function NearbyPoiView() {

    const navigate = useNavigate();

    const [user, setUser] = useState<SocialUser>();
    const [position, setPosition] = useState<{lat: number, lng: number}>({lat: 43, lng: -8});
    const [centroid, setCentroid] = useState<LatLng | null>();
    const [nearbyPoints, setNearbyPoints] = useState<PointOfInterest[] | null>(null)
    const [selectedPoint, setSelectedPoint] = useState<PointOfInterest | null>(null);
    const { isLoaded } = useLoadScript({
        googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
    })

    useEffect(() => {
        UserAuthEndpoint.getUser().then(setUser);
    }, [])

    if (!isLoaded)
        return <CircularProgress />

    return (

        <Box style={{
            backgroundImage: "url(https://www.eea.europa.eu/highlights/eight-facts-about-europe2019s-forest-ecosystems/image_print)",
            backgroundPosition: 'center',
            backgroundSize: 'cover',
            backgroundRepeat: 'space',
            height: "100%"
        }}>
            <SocialAppBar />

            <Container maxWidth={"md"}>
                <Card elevation={3}>
                    <Grid container spacing={8} padding="20px">
                        <Grid item xs={7}>
                            <Typography variant="h2">Nearby spots</Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Grid item xs={12} md={6}>
                                <List>
                                    <Chip label={"Cafe"} sx={{marginRight: "10px"}} onClick={_ => {
                                        setNearbyPoints([])
                                        setCentroid(null)
                                        loadNearbyPoints(SocialPlaceType.CAFE)
                                    }}/>
                                    <Chip label={"Park"} sx={{marginRight: "10px"}} onClick={_ => {
                                        setNearbyPoints(null)
                                        setCentroid(null)
                                        loadNearbyPoints(SocialPlaceType.PARK)
                                    }}/>
                                    <Chip label={"Restaurant"} sx={{marginRight: "10px"}} onClick={_ => {
                                        setNearbyPoints(null)
                                        setCentroid(null)
                                        loadNearbyPoints(SocialPlaceType.RESTAURANT)
                                    }}/>
                                    <Chip label={"Florist"} sx={{marginRight: "10px"}} onClick={_ => {
                                        setNearbyPoints(null)
                                        setCentroid(null)
                                        loadNearbyPoints(SocialPlaceType.FLORIST)
                                    }}/>
                                    <Chip label={"Night club"} sx={{marginRight: "10px"}} onClick={_ => {
                                        setNearbyPoints(null)
                                        setCentroid(null)
                                        loadNearbyPoints(SocialPlaceType.NIGHT_CLUB)
                                    }}/>
                                </List>
                            </Grid>
                        </Grid>
                        <Grid item xs={7}>
                            {!isLoaded || position == null &&

                                <Placeholder/>

                            }

                            {isLoaded && position != null &&

                                <GoogleMap options={{disableDefaultUI: true, gestureHandling: "none"}} mapContainerClassName="map-container" zoom={12} center={{lat:position!.lat, lng:position!.lng}}>

                                    {nearbyPoints != null && nearbyPoints.filter(p => p != null).map((value, index, array) =>

                                        <Marker position={value.coordinates} label={(selectedPoint != null && selectedPoint == value)? selectedPoint.name : ""}/>

                                    )}

                                </GoogleMap>

                            }
                        </Grid>
                        <Grid item xs={5}>
                            <Paper style={{maxHeight: 500, overflow: 'auto'}}>
                                <List>
                                    {getPoiListItems()}
                                </List>
                            </Paper>
                        </Grid>

                    </Grid>
                </Card>
            </Container>

        </Box>

    );

    function getPoiListItems() {

        if (nearbyPoints === undefined || nearbyPoints === null) {
            return;
        }

        return (
            nearbyPoints.map((poi, index) => {
                console.log(poi!.name);
                return (
                    <Grid key={poi!.id} container>
                        <Grid item xs={7}>
                            <Button variant="text" onClick={_ =>{
                                const poi = nearbyPoints[index];
                                setPosition({ lat:poi!.coordinates.lat, lng: poi!.coordinates.lng });
                            }}>
                                <Grid container>
                                    <Grid item xs={12} textAlign={"start"}>
                                        <Typography variant="h5">{poi!.name}</Typography>
                                    </Grid>
                                    <Grid item xs={12} textAlign={"start"}>
                                        {poi!.types[0] + " | Rating:" + poi!.rating}
                                    </Grid>
                                </Grid>
                            </Button>
                        </Grid>
                        <Grid item xs={2} alignSelf={"center"} style={{height: "100%"}}>
                            <Box style={{height: "100%"}}>
                                <Button variant="text" onClick={_ => goToPoiView(nearbyPoints[index]!.id!)}><Info height={"100%"} /></Button>
                            </Box>
                        </Grid>
                    </Grid>
                )
            })
        )
    }

    function goToPoiView(id: number) {

        navigate("/point?point_id=" + id);

    }

    async function loadNearbyPoints(type: SocialPlaceType ) {

        try {

            if (user == null) {
                console.log("user null");
                return;
            }

            MeetingEndpoint.calculateCentroid([user]).then(setCentroid);

            const points = await PointOfInterestEndpoint.findClosePointsOfInterest([user!], type);

            setNearbyPoints(points)

        } catch (_) {

        }

    }

}


