import {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import SocialAppBar from "Frontend/components/SocialAppBar";
import UserGroup from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/UserGroup";
import {UserGroupEndpoint} from "Frontend/generated/endpoints";
import {List, ListItem} from "@mui/material";
import ListItemText from '@mui/material/ListItemText';
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import React from "react";

export default function GroupView() {

    const [groups, setGroups] = useState<UserGroup[]>([])

    let groupListItems = groups.map(value =>
        <ListItem>
            <ListItemText>{value.name}</ListItemText>
        </ListItem>
    )

    const loadUserGroups = async () => {
        const groups = await UserGroupEndpoint.getUserGroups();
        setGroups(groups)

        console.log(groups)

        groupListItems = groups.map(value =>
            <ListItem key={value.id}>
                <ListItemText primary={value.name}/>
            </ListItem>
        )
    }

    useEffect(() => {
        loadUserGroups()
    }, []);

    return(

        <Box>
            <SocialAppBar/>
            <Container fixed style={{backgroundColor: "rgb(155,155,155)"}}>

                <Typography variant={"h4"}>Grupos a los que perteneces:</Typography>
                <List sx={{ width: '100%', maxWidth: 360 }}>
                    {groupListItems}
                </List>

            </Container>

        </Box>

    );

}