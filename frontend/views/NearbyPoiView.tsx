import {Card, Box, Container, List, ListItemButton} from "@mui/material";
import SocialAppBar from "Frontend/components/SocialAppBar";
import Grid from "@mui/material/Grid";
import {useEffect, useState} from "react";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest";
import Typography from "@mui/material/Typography";
import {GoogleMap, Marker, useLoadScript} from "@react-google-maps/api";
import CircularProgress from "@mui/material/CircularProgress";
import {PointOfInterestEndpoint, UserAuthEndpoint} from "Frontend/generated/endpoints";
import Button from "@mui/material/Button";
import {Info} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";

export default function NearbyPoiView() {

    const navigate = useNavigate();

    const [user, setUser] = useState<SocialUser>();
    const [position, setPosition] = useState<{lat: number, lng: number}>();
    const [zoom, setZoom] = useState<number>(12)
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
                    <Grid container spacing={12} padding="20px">

                        <Grid item xs={7}>
                            <Typography variant="h2">Nearby spots</Typography>
                        </Grid>
                        <Grid item xs={7}>
                            <GoogleMap
                                zoom={zoom}
                                center={position || { lat: 43, lng: -8}}
                                mapContainerClassName="map-container"
                            >
                                {position &&
                                    <Marker position={position} />
                                }
                            </GoogleMap>
                        </Grid>
                        <Grid item xs={5}>
                            <List>
                                {getPoiListItems()}
                            </List>
                        </Grid>

                    </Grid>
                </Card>
            </Container>

        </Box>

    );

    function getPoiListItems() {

        const poiList = user?.residence?.nearbyPointsOfInterest!;

        if (poiList === undefined) {
            return;
        }

        if (poiList.length < 1)
            return (
                <ListItemButton>
                    Se este botón é visible non hai puntos de interese
                    <Button variant="text"><Info /></Button>
                </ListItemButton>
            )

        return (
            poiList.map((poi, index) => {
                console.log(poi!.name);
                return (
                    <Grid key={poi!.id} container>
                        <Grid item xs={7}>
                            <Button variant="outlined" onClick={_ =>{
                                const poi = poiList[index];
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
                                <Button variant="text" onClick={_ => goToPoiView(poiList[index]!.id!)}><Info height={"100%"} /></Button>
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

}


