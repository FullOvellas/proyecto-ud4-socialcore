import {UserGroupEndpoint} from "Frontend/generated/endpoints";
import React, {useEffect, useState} from "react";
import UserGroup from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/UserGroup";
import SocialAppBar from "Frontend/components/SocialAppBar";
import Container from "@mui/material/Container";
import {Card, Divider, IconButton, ListItem, ListItemAvatar, ListItemButton, ListItemText, Modal} from "@mui/material";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import {useNavigate, useSearchParams} from "react-router-dom";
import {List} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import {AddCircleOutlined} from "@mui/icons-material";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import RemoveIcon from '@mui/icons-material/Remove';
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import {EndpointError} from "@hilla/frontend";

export default function GroupView() {

    const navigate = useNavigate()

    const [userMail, setUserMail] = useState<string>("");
    const [searchParams, setSearchParams] = useSearchParams();
    const [showModal, setShowModal] = useState<boolean>(false);
    const [group, setGroup] = useState<UserGroup | null>(null);
    const [groupId, setGroupId] = useState<number>(-1);
    const [isCreator, setIsCreator] = useState<boolean>(false);
    const [error, setError] = useState<String>("");

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

    const addUserToGroup = async () => {

        if(group == null) {
            return;
        }

        try {

            await UserGroupEndpoint.addUserToGroup(userMail, group!)
            setShowModal(false)
            setUserMail("")
            setError("")

            loadGroup(groupId)

        } catch (e) {

            if(e instanceof EndpointError ) {

                if(e.type != undefined ) {

                    if(e.type.endsWith("UserNotFoundException")) {

                        setError("User not found")

                    } else if(e.type.endsWith("ForbidenAccessException")) {

                        setError("Forbiden access")

                    }

                }

            }

        }

    };

    const removeUserFromGroup = async (user: SocialUser) => {

        if(group == null) {
            return;
        }

        try {

            if(user.id == group!.creator.id) {

                // TODO: mostrar error
                return;
            }

            await UserGroupEndpoint.removeUserFromGroup(group!, user)

            loadGroup(groupId)

        } catch (e) {

            if(e instanceof EndpointError ) {

                if(e.type != undefined ) {

                    if(e.type.endsWith("ForbidenAccessException")) {

                        // TODO: mostrar error ??

                    }

                }

            }

        }

    }

    const loadGroup = async (id: number) => {

        try {

            let newGroup = await UserGroupEndpoint.getGroupById(id);

            setGroup(newGroup)

            setIsCreator(await UserGroupEndpoint.isCreator(newGroup))

        } catch (error) {

            console.log(error)

        }

    }

    const deleteGroup = async () => {

        // TODO: añadir confirmación de borrado

        await UserGroupEndpoint.deleteGroup(group!);

        navigate("/profile")


    }

    useEffect( () => {

        if(searchParams.get("group_id") != null ) {

            let idString = searchParams.get("group_id")!.toString()
            let id = parseInt(idString)

            setGroupId(id)

            loadGroup(id)


        }

    }, []);

    return(

        <Box>

            <SocialAppBar/>

            <Modal onClose={ _ => setShowModal(false)} open={showModal}>

                <Box sx={{ ...modalStyle, width: 300 }}>

                    <List>

                        <ListItem><ListItemText><h3 id="child-modal-title">Add new user</h3></ListItemText></ListItem>

                        <ListItem><TextField label="User email" error={error != ""} autoFocus={true} onEnded={addUserToGroup} onSubmit={addUserToGroup} name="groupName" value={userMail} onChange={event => setUserMail(event.currentTarget.value)} /></ListItem>

                        <ListItem><Typography variant={"body2"} color={"darkred"}>{error}</Typography></ListItem>

                        <ListItem secondaryAction={
                            <Button variant={"contained"} onClick={ _ => {addUserToGroup()}}>
                                Add
                            </Button>
                        }>

                                <Button onClick={ _ => setShowModal(false)}>Cancel</Button>

                        </ListItem>

                    </List>

                </Box>

            </Modal>

            <Container maxWidth={"md"}>

                <Card elevation={3}>

                    <Grid container spacing={12} padding={"20px"}>

                        <Grid item xs={12}>

                            <Typography paddingBottom={"10px"} textAlign="center" variant={"h2"}>{(group)? group!.name : ""}</Typography>


                        </Grid>

                        <Grid item md={6} xs={12}>

                            <ListItem>

                                <ListItemText><Typography paddingBottom={"10px"} variant={"h4"}>Creator</Typography></ListItemText>

                            </ListItem>

                            <Card>

                                <ListItem>
                                    <ListItemAvatar>
                                        <Avatar></Avatar>
                                    </ListItemAvatar>
                                    <ListItemText><Typography variant={"body2"}>{(group)? group.creator!.fullName : ""}</Typography></ListItemText>
                                </ListItem>

                            </Card>

                            {isCreator &&

                                <List>

                                    <ListItem><Typography variant="h5">Opciones de creador</Typography></ListItem>
                                    <ListItem><Button onClick={ _ => deleteGroup()}>Delete group</Button></ListItem>

                                </List>

                            }

                        </Grid>

                        <Grid item xs={12} md={6}>

                            <ListItem secondaryAction={<IconButton onClick={ _ => setShowModal(true) }><AddCircleOutlined/></IconButton>}>
                                <ListItemText><Typography paddingBottom={"10px"} variant={"h4"}>Participants</Typography></ListItemText>
                            </ListItem>

                            <List>

                                {
                                    (group!= null)? group.participants!.map(value =>

                                        <ListItem key={"participant_" + value!.id}>
                                            <ListItemAvatar>
                                                <Avatar></Avatar>
                                            </ListItemAvatar>
                                            <ListItemText><Typography variant={"body2"}>{value!.fullName}</Typography></ListItemText>
                                            { value!.id != group.creator.id &&
                                                <IconButton onClick={ _ => removeUserFromGroup(value!)}><RemoveIcon/></IconButton>
                                            }
                                        </ListItem>

                                    ) : <p>Not found</p>
                                }

                            </List>

                        </Grid>

                        <Grid item xs={12} md={12}>

                            <Typography paddingBottom={"10px"} textAlign="center" variant={"h4"}>Meetings</Typography>

                        </Grid>

                    </Grid>

                </Card>

            </Container>

        </Box>

    );

}