import SocialAppBar from "Frontend/components/SocialAppBar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import {useEffect, useState} from "react";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import {MeetingEndpoint, UserAuthEndpoint, UserEndpoint} from "Frontend/generated/endpoints";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import Grid from "@mui/material/Grid";
import {Card, ListItem, ListItemSecondaryAction, ListItemText} from "@mui/material";
import Typography from "@mui/material/Typography";
import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";

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
            .then(v => {

                if(v) {
                    navigate("/")
                    return
                }

                UserAuthEndpoint.getUser()
                    .then(user => {

                        setUser(user);

                        loadMeeting
                            .then(setMeeting)
                            .catch(_ => navigate("/"))

                    })

            })

    }, []);

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

                        <Grid item xs={12} md={6}>

                            <Typography variant="h4" textAlign="center">
                                Details
                            </Typography>

                            <ListItem>
                                <ListItemText>Date: {}</ListItemText>
                            </ListItem>

                        </Grid>

                    </Grid>

                </Card>

            </Container>

        </Box>

    );

}