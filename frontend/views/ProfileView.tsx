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
import {AddCard} from "@mui/icons-material";
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
            <ListItemText primary={value.name} secondary={"Creador: " + value.creator.fullName}/>
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

        <Box>

            <SocialAppBar/>

            <Container maxWidth={"md"}>

                <Modal onClose={ _ => setCreateGroupModalShown(false)} open={createGroupModalShown}>

                    <Box sx={{ ...modalStyle, width: 300 }}>

                        <List>

                            <ListItem><ListItemText><h4 id="child-modal-title">Crear nuevo grupo</h4></ListItemText></ListItem>

                            <ListItem><TextField label="Nombre del grupo" autoFocus={true} onEnded={createUserGroup} onSubmit={createUserGroup} name="groupName" value={newGroupName} onChange={event => setNewGroupName(event.currentTarget.value)} /></ListItem>

                            <ListItem secondaryAction={<Button variant={"contained"} onClick={ _ => {

                                createUserGroup()

                            }
                            }>Crear</Button>}>

                                <Button onClick={ _ => setCreateGroupModalShown(false)}>Cancelar</Button>

                            </ListItem>

                        </List>

                    </Box>

                </Modal>

                <Card elevation={3}>

                    <Grid container spacing={12} padding={"20px"}>

                        <Grid item xs={12}>

                            <Typography paddingBottom={"10px"} variant={"h2"}>{(user)? user!.fullName : ""}</Typography>
                            <Typography paddingBottom={"10px"} variant={"h5"} color={"rgb(155,155,155)"}>{(user)? user!.email : ""}</Typography>

                        </Grid>

                        <Grid item xs={12}>

                            <Typography paddingBottom={"10px"} variant={"h5"}>Lista de amigos</Typography>

                        </Grid>

                        <Grid item xs={12} md={6}>

                            <Card elevation={3} style={{borderRadius: "15px"}}>

                                <ListItem secondaryAction={<IconButton onClick={_ => setCreateGroupModalShown(true)}><AddCard/></IconButton>}>

                                    <ListItemText><Typography variant={"h5"}>Grupos a los que perteneces:</Typography></ListItemText>

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
                                        <ListItemText><Typography variant={"h5"}>Quedadas recientes:</Typography></ListItemText>
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