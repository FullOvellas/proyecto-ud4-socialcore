import {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import SocialAppBar from "Frontend/components/SocialAppBar";
import UserGroup from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/UserGroup";
import {UserAuthEndpoint, UserGroupEndpoint} from "Frontend/generated/endpoints";
import {Card, Divider, IconButton, List, ListItem, Modal, Paper} from "@mui/material";
import ListItemText from '@mui/material/ListItemText';
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import React from "react";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import {Add, AddCard} from "@mui/icons-material";
import TextField from "@mui/material/TextField";
import {useNavigate} from "react-router-dom";

export default function ProfileView() {

    const navigate = useNavigate()

    const [groups, setGroups] = useState<UserGroup[]>([]);
    const [user, setUser] = useState<SocialUser>();
    const [newGroupName, setNewGroupName] = useState<string>("");
    const [createGroupModalShown, setCreateGroupModalShown] = useState<boolean>(false);

    const modalStyle = {
        position: 'absolute' as 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        borderRadius: "30px",
        boxShadow: 24,
        pt: 2,
        px: 4,
        pb: 3,
    };

    let groupListItems = groups.map(value =>
        <ListItem key={"group_item_" + value.id} style={{cursor: "pointer"}} onClick={(_)=> openUserGroup(value)}>
            <ListItemText primary={value.name} secondary={"Creator: " + value.creator.fullName}/>
        </ListItem>
    )

    const loadUserGroups = async () => {
        const groups = await UserGroupEndpoint.getUserGroups();
        setGroups(groups)
    }

    const openUserGroup= (group: UserGroup) => {

        // Abrir página de detalles de grupo de usuarios
        navigate("/group?group_id=" + group.id)

    }

    const goToNearbyPoiList = (user: SocialUser) => {

        // Abrir la página de vista de puntos de interés cercanos
        navigate("/nearby?user=" + user.id);

    }

    const createUserGroup = async () =>  {

        let created = await UserGroupEndpoint.createUserGroup(newGroupName)

        if(created) {

            loadUserGroups()

        }

        setCreateGroupModalShown(false);
        setNewGroupName("")

    }

    useEffect(() => {
        loadUserGroups()
        UserAuthEndpoint.getUser().then(setUser)
    }, []);

    return(

        <Box style={{
            backgroundImage: "url(https://www.eea.europa.eu/highlights/eight-facts-about-europe2019s-forest-ecosystems/image_print)",
            backgroundPosition: 'center',
            backgroundSize: 'cover',
            backgroundRepeat: 'space',
            height: "100%"
        }}>

            <SocialAppBar/>

            <Container maxWidth={"md"}>

                <Modal onClose={ _ => setCreateGroupModalShown(false)} open={createGroupModalShown}>

                    <Box sx={{ ...modalStyle, width: 300 }}>

                        <List>

                            <ListItem><ListItemText><h4 id="child-modal-title">Create a new group</h4></ListItemText></ListItem>

                            <ListItem><TextField label="Group name" autoFocus={true} onEnded={createUserGroup} onSubmit={createUserGroup} name="groupName" value={newGroupName} onChange={event => setNewGroupName(event.currentTarget.value)} /></ListItem>

                            <ListItem secondaryAction={<Button variant={"contained"} onClick={ _ => {

                                createUserGroup()

                            }
                            }>Create</Button>}>

                                <Button onClick={ _ => setCreateGroupModalShown(false)}>Cancel</Button>

                            </ListItem>

                        </List>

                    </Box>

                </Modal>

                <Card elevation={3}>

                    <Grid container spacing={12} padding={"20px"}>

                        <Grid item xs={8}>

                            <Typography paddingBottom={"10px"} variant={"h2"}>{(user)? user!.fullName : ""}</Typography>
                            <Typography paddingBottom={"10px"} variant={"h5"} color={"rgb(155,155,155)"}>{(user)? user!.email : ""}</Typography>

                        </Grid>

                        <Grid item xs={4} alignSelf="center">
                            <Button onClick={_ => goToNearbyPoiList(user!)} variant="outlined">Find meeting spots</Button>
                        </Grid>

                        <Grid item xs={12} md={6}>

                            <Card elevation={3} style={{borderRadius: "15px"}}>

                                <ListItem secondaryAction={<IconButton onClick={_ => setCreateGroupModalShown(true)}><Add/></IconButton>}>

                                    <ListItemText><Typography variant={"h5"}>Groups:</Typography></ListItemText>

                                </ListItem>

                                <Divider/>

                                <List sx={{ width: '100%', overflow: "auto", maxWidth: 360, maxHeight: 300, bgcolor: 'background.paper'}}>

                                    {groupListItems}

                                </List>

                            </Card>

                        </Grid>

                        <Grid item xs={12} md={6}>

                            <Card elevation={3} style={{borderRadius: "15px"}}>

                                <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>

                                    <ListItem>

                                        <ListItemText><Typography variant={"h5"}>Recent meetings:</Typography></ListItemText>

                                    </ListItem>

                                </List>

                            </Card>

                        </Grid>

                    </Grid>

                </Card>

            </Container>

        </Box>

    );

}