import {AppBar, IconButton, Toolbar} from "@mui/material";
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

    useLayoutEffect(() => {

        const loadUser = async () => {

            if(!await UserAuthEndpoint.isAnonymous() ) {

                const userName = await UserAuthEndpoint.getUserName();

                setUser(userName);

            }

        }

        loadUser()

    }, []);

    return (
    <AppBar position="static">
        <Toolbar>
            <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                sx={{ mr: 2 }}
            >
                <MenuIcon />
            </IconButton>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                SocialCore
            </Typography>
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
                        <MenuItem onClick={event => navigate("/profile")}>Perfil</MenuItem>
                    </Menu>
                </div>
            )}
            {user == "" && (
                <div>
                    <Button variant={"contained"} onClick={event => navigate("/login")} style={{backgroundColor: "#FFF"}}>
                        <Typography color={"primary"}>
                            Iniciar sesión
                        </Typography>
                    </Button>
                    <Button variant={"contained"} onClick={event => navigate("/register")} style={{backgroundColor: "#FFF"}}>
                        <Typography color={"primary"}>
                            Registrarse
                        </Typography>
                    </Button>
                </div>
            )}
        </Toolbar>
    </AppBar>
);

}