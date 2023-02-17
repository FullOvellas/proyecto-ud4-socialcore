import React, {useEffect, useLayoutEffect, useState} from "react";
import {UserAuthEndpoint} from "Frontend/generated/endpoints";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Menu from "@mui/material/Menu";
import MenuItem from '@mui/material/MenuItem';
import {AppBar, Card, IconButton, Toolbar} from "@mui/material";

import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import {Padding} from "@mui/icons-material";
import Button from "@mui/material/Button";
import {createTheme} from "@mui/material/styles";
import {useNavigate} from "react-router-dom";
import SocialAppBar from "Frontend/components/SocialAppBar";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";
import Placeholder from "Frontend/components/placeholder/Placeholder";
import Container from "@mui/material/Container";
import ProfileView from "Frontend/views/ProfileView";
import Grid from "@mui/material/Grid";

export default function EmptyView() {

    const [user, setUser] = useState<SocialUser|null|undefined>(undefined);

    let navigate = useNavigate()

    useEffect( () => {

        UserAuthEndpoint.isAnonymous().then(value => {

            if(value) {

                setUser(null);

            } else {

                UserAuthEndpoint.getUser().then(value1 => {

                    if(value1 != null) {

                        setUser(value1);

                    }

                })

            }

        })

    }, [])

  return (
      <>

          {user === undefined &&

              <Placeholder/>

          }

          {user === null &&

              <Box sx={{backgroundImage: "url(https://www.eea.europa.eu/highlights/eight-facts-about-europe2019s-forest-ecosystems/image_print)",
                  backgroundPosition: 'center',
                  backgroundSize: 'cover',
                  backgroundRepeat: 'space',
                  height: "100%"}}>

                  <SocialAppBar/>

                  <Container maxWidth="md">

                      <Card elevation={12}>

                          <Grid container>

                              <Grid item xs={12} md={12} padding={7}>

                                  <Typography variant="h2">Sign up / Login to create plans </Typography>

                              </Grid>

                              <Grid item xs={12} md={12} padding={7}>

                                  <Button onClick={_=> navigate("/register")} variant={"outlined"} sx={{marginRight: "40px"}}>
                                      Sign up
                                  </Button>

                                  <Button variant={"contained"} onClick={_=> navigate("/login")}>
                                      Login
                                  </Button>

                              </Grid>

                          </Grid>

                      </Card>

                  </Container>

              </Box>

          }

          {user != null &&

              <ProfileView/>

          }

      </>
  );
}
