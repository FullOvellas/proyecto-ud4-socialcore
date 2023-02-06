import {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import SocialAppBar from "Frontend/components/SocialAppBar";
import UserGroup from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/UserGroup";
import {UserAuthEndpoint, UserGroupEndpoint} from "Frontend/generated/endpoints";
import {Card, List, ListItem, Paper} from "@mui/material";
import ListItemText from '@mui/material/ListItemText';
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import React from "react";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";

export default function ProfileView() {

    const [groups, setGroups] = useState<UserGroup[]>([])
    const [user, setUser] = useState<SocialUser>()

    const openUserGroup= (group: UserGroup) => {

        // Abrir página de detalles de grupo de usuarios

    }

    let groupListItems = groups.map(value =>
        <ListItem key={"group_item_" + value.id} style={{cursor: "pointer"}} onClick={(_)=> openUserGroup(value)}>
            <ListItemText primary={value.name} secondary={"Creador: " + value.creator.fullName}/>
        </ListItem>
    )

    const loadUserGroups = async () => {
        const groups = await UserGroupEndpoint.getUserGroups();
        setGroups(groups)
    }

    useEffect(() => {
        loadUserGroups()
        UserAuthEndpoint.getUser().then(setUser)
    }, []);

    return(

        <Box>

            <SocialAppBar/>

            <Container maxWidth={"md"}>

                <Card elevation={3}>

                    <Grid container spacing={12} padding={"20px"}>

                        <Grid item xs={12}>

                            <Typography paddingBottom={"10px"} variant={"h2"}>{(user)? user!.fullName : ""}</Typography>
                            <Typography paddingBottom={"10px"} variant={"h5"} color={"rgb(155,155,155)"}>{(user)? user!.email : ""}</Typography>

                        </Grid>

                        <Grid item xs={12} md={6}>

                            <Typography variant={"h4"}>Grupos a los que perteneces:</Typography>

                            <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>
                                {groupListItems}
                            </List>

                        </Grid>

                        <Grid item xs={12} md={6}>

                            <Typography variant={"h4"}>Quedadas recientes:</Typography>
                            <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>

                            </List>

                        </Grid>

                    </Grid>

                </Card>

            </Container>

        </Box>

    );

}