import SocialAppBar from "Frontend/components/SocialAppBar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import React, {useEffect, useState} from "react";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import {MeetingEndpoint, UserAuthEndpoint, UserEndpoint} from "Frontend/generated/endpoints";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import Grid from "@mui/material/Grid";
import {
    Card,
    IconButton,
    ImageList, ImageListItem,
    List,
    ListItem,
    ListItemAvatar,
    ListItemSecondaryAction,
    ListItemText
} from "@mui/material";
import Typography from "@mui/material/Typography";
import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";
import MeetingMap from "Frontend/components/MeetingMap"
import Avatar from "@mui/material/Avatar";
import Rating from "@mui/material/Rating"

export default function MeetingView() {

    const navigate = useNavigate()

    const [user, setUser] = useState<SocialUser>();
    const [meeting, setMeeting] = useState<Meeting>();
    const [searchParams, setSearchParams] = useSearchParams();

    const loadMeeting = new Promise<Meeting>(async (resolve, reject) => {


        if(searchParams.get("meeting_id") != null ) {

            let idString = searchParams.get("meeting_id")!.toString()
            let id = parseInt(idString)

            return resolve(MeetingEndpoint.getMeetingFromId(id))
        }

        return reject(new Error(""));
    })

    useEffect(() => {

        UserAuthEndpoint.isAnonymous()
            .then(isAnonymous => {

                if(isAnonymous) {
                    navigate("/");
                    return;
                }

                UserAuthEndpoint.getUser()
                    .then(user => {

                        setUser(user);

                        loadMeeting
                            .then(setMeeting)
                            .catch(_ => navigate("/"));

                    })

            })

    }, []);

    console.log(meeting?.destination.rating);

    return (

        <Box>

            <SocialAppBar/>

            <Container maxWidth="md">

                <Card elevation={3}>

                    <Grid container spacing="12">

                        <Grid xs={12} md={12} item>

                            <Typography variant="h2" textAlign="center">
                                Meeting
                            </Typography>

                        </Grid>

                        <Grid item xs={12} md={12}>

                        { meeting != null &&
                            <MeetingMap meeting={meeting} />
                        }

                        </Grid>

                        <Grid item xs={6} md={6}>
                            <ListItemText><Typography paddingBottom={"10px"} variant={"h4"}>Participants</Typography></ListItemText>
                            <List>
                                {
                                    meeting?.attendants!.map(value =>

                                        <ListItem key={"attendant_" + value!.id}>
                                            <ListItemAvatar>
                                                <Avatar></Avatar>
                                            </ListItemAvatar>
                                            <ListItemText><Typography variant={"body2"}>{value!.fullName}</Typography></ListItemText>
                                        </ListItem>

                                    )
                                }
                            </List>
                        </Grid>
                        <Grid item xs={6} md={6}>
                            <ListItemText><Typography paddingBottom={"10px"} variant={"h4"}>Meeting spot</Typography></ListItemText>
                            <ImageList>
                                <ImageListItem>
                                    <img src={
                                        URL.createObjectURL(
                                            new Blob(
                                                [new Uint8Array(meeting?.destination.imageData!)],
                                                { type: 'image/png' })
                                        )
                                    }
                                    />
                                </ImageListItem>
                            </ImageList>
                            <List>
                                <ListItem>
                                    <ListItemText>
                                        <Typography variant={"h6"}>Place name </Typography>
                                        {meeting?.destination.name}
                                    </ListItemText>
                                </ListItem>
                                <ListItem>
                                    <ListItemText>
                                        <Typography variant={"h6"}>Address:</Typography>
                                        {meeting?.destination.businessStatus}
                                    </ListItemText>
                                </ListItem>
                                {
                                    meeting?.destination.openingHours.weekdayText !== undefined?
                                    (
                                        <ListItem>
                                            <ListItemText>
                                                <Typography variant={"h6"}>Opening hours:</Typography>
                                                {meeting?.destination.openingHours.weekdayText![0]}
                                            </ListItemText>
                                        </ListItem>
                                    ) : null
                                }
                                <ListItem>
                                    <ListItemText>
                                        <Typography variant={"h6"}>Rating:</Typography>
                                        <Rating value={meeting?.destination.rating || 0} precision={0.5} readOnly={true} />
                                    </ListItemText>
                                </ListItem>

                            </List>
                        </Grid>

                    </Grid>

                </Card>

            </Container>

        </Box>

    );

}