import {AppBar, IconButton, ListItemButton, Toolbar} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import Typography from "@mui/material/Typography";
import AccountCircle from "@mui/icons-material/AccountCircle";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import React, {useLayoutEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserAuthEndpoint} from "Frontend/generated/endpoints";

export default function SocialAppBar() {

    let navigate = useNavigate()

    const [user, setUser] = useState<String>("");
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const loadUser = async () => {

        if(!await UserAuthEndpoint.isAnonymous() ) {

            const userName = await UserAuthEndpoint.getUserName();

            setUser(userName);

        }

    };

    useLayoutEffect(() => {

        loadUser()

    }, []);

    return (

        <div>

            <AppBar position="static">
                <Toolbar>
                    <ListItemButton onClick={_=> navigate("/")}>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                            SocialCore
                        </Typography>
                    </ListItemButton>
                    {user != "" && (
                        <div>
                            <IconButton
                                size="large"
                                aria-label="account of current user"
                                aria-controls="menu-appbar"
                                aria-haspopup="true"
                                onClick={handleMenu}
                                color="inherit"
                            >
                                <Typography>{user}</Typography>
                                <AccountCircle />
                            </IconButton>
                            <Menu
                                id="menu-appbar"
                                anchorEl={anchorEl}
                                anchorOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                keepMounted
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                open={Boolean(anchorEl)}
                                onClose={handleClose}
                            >
                                <MenuItem onClick={ _ =>
                                    fetch("/logout", {
                                        method: "GET",
                                    }).then(v => {
                                        if(v.redirected) window.location.replace(v.url);
                                    }).catch( _ => console.log("Error"))
                                }>Log out</MenuItem>
                            </Menu>
                        </div>
                    )}
                    {user == "" && (
                        <div>
                            <Button variant={"contained"} onClick={ _ => navigate("/login")} style={{backgroundColor: "#FFF"}}>
                                <Typography color={"primary"}>
                                    Login
                                </Typography>
                            </Button>
                            <Button variant={"contained"} onClick={ _ => navigate("/register")} style={{backgroundColor: "#FFF"}}>
                                <Typography color={"primary"}>
                                    Sign up
                                </Typography>
                            </Button>
                        </div>
                    )}
                </Toolbar>
            </AppBar>

        </div>
);

}