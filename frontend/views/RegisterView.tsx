import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {ListItem, Step, StepButton, StepContent, StepLabel, Stepper} from "@mui/material";
import {GoogleMap, Marker, useLoadScript} from "@react-google-maps/api";
import Placeholder from "Frontend/components/placeholder/Placeholder";
import {List} from "@mui/icons-material";
import LatLng = google.maps.LatLng;

export default function RegisterView() {

    const theme = createTheme();

    const [email, setEmail] = useState<string>("");
    const [fullName, setFullName] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [passwordMatch, setPasswordMatch] = useState<string>("");
    const [latlng, setLatlng] = useState<LatLng | null>(null);

    const [activeStep, setActiveStep] = useState<number>(0);
    const [error, setError] = useState<string>("")
    const [searchParams, setSearchParams] = useSearchParams();

    const {isLoaded} = useLoadScript({
        googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
    })

    function handleNext() {

        if(!checkForm()) {
            return;
        }

        setActiveStep(1);

    }

    function handlePrevious() {

        setActiveStep(0);

    }

    function handleMapselect(event: google.maps.MapMouseEvent) {

        setLatlng(event.latLng)

    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (!checkForm()) {
            return;
        }

        fetch("/register", {
            method: "POST",
            body: new URLSearchParams([["email", email!.toString()], ["fullName", fullName!.toString()], ["password", password!.toString()], ["lat", latlng!.lat!.toString()], ["lng", latlng!.lng!.toString()]])
        }).then(v => {

            if (v.redirected) window.location.replace(v.url);

        }).catch(e => console.log("Error"))

    }

    useEffect(() => {

        if (searchParams.get("error") != null) {

            setError("Invalid credentials")

        }

    });

    function checkForm(): boolean {

        if (email == "" || fullName == "" || password == "" || passwordMatch == "") {
            setError("Fields can't be empty");
            return false;
        }

        if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email!.toString())) {
            setError("Email is not valid");
            return false;
        }

        if (password != passwordMatch) {
            setError("Passwords don't match");
            return false;
        }

        if (password!.length < 5) {
            setError("Password must have at least 5 characters");
            return false;
        }

        setError("");

        return true;
    }

    const stepLabels = ["Credentials", "User location"];

    return (
        <ThemeProvider theme={theme}>
            <Container component={"main"} maxWidth={"xs"}>
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >

                    <Container>

                        <Grid container>

                            <Grid item xs={12} md={12}>

                                <Typography textAlign="center" component="h1" variant="h5">
                                    Sign up
                                </Typography>

                            </Grid>

                            <Grid item xs={12} md={12}>

                                <form onSubmit={handleSubmit}>

                                    <Box sx={{ width: "100%", margin: "auto" }}>

                                        <Stepper dir={"ltr"} activeStep={1} orientation={"vertical"}>

                                            <Step>

                                                <StepLabel>{stepLabels[0]}</StepLabel>

                                                <StepContent>

                                                    <ul>
                                                        <TextField
                                                            required
                                                            value={fullName}
                                                            onChange={event => setFullName(event.target.value)}
                                                            id="fullName"
                                                            label="Name"
                                                            margin="normal"
                                                            name="fullName"
                                                            autoComplete="name"
                                                        />
                                                        <TextField
                                                            required
                                                            value={email}
                                                            onChange={event => setEmail(event.target.value)}
                                                            id="email"
                                                            margin="normal"
                                                            label="Email"
                                                            name="email"
                                                            autoComplete="email"
                                                        />
                                                        <TextField
                                                            required
                                                            value={password}
                                                            onChange={event => setPassword(event.target.value)}
                                                            name="password"
                                                            margin="normal"
                                                            label="Password"
                                                            type="password"
                                                            id="password"
                                                            autoComplete="current-password"
                                                        />
                                                        <TextField
                                                            margin="normal"
                                                            required
                                                            value={passwordMatch}
                                                            onChange={event => setPasswordMatch(event.target.value)}
                                                            name="password_rep"
                                                            label="Repeat password"
                                                            type="password"
                                                            id="password_rep"
                                                            autoComplete="current-password"
                                                        />

                                                        <Typography variant={"caption"} color={"rgb(155,155,155)"}>* Password must have
                                                            at least 5 characters
                                                        </Typography>

                                                        {error != "" &&
                                                            <Typography color={"#FF0000"}>{error}</Typography>
                                                        }

                                                        <Button fullWidth={true} onClick={handleNext} variant={"contained"}>Next</Button>

                                                    </ul>

                                                </StepContent>

                                            </Step>

                                            <Step>

                                                <StepLabel>{stepLabels[1]}</StepLabel>

                                                <StepContent>
                                                    {!isLoaded &&
                                                        <Placeholder/>
                                                    }

                                                    {isLoaded &&
                                                        <GoogleMap onClick={handleMapselect} mapContainerClassName="map-container" zoom={7} center={(latlng != null)? latlng : {lat: 42.715756, lng: -7.947729}}>

                                                            {latlng != null &&

                                                                <Marker label={"Home"} position={latlng}></Marker>

                                                            }

                                                        </GoogleMap>
                                                    }
                                                </StepContent>

                                            </Step>

                                        </Stepper>

                                    </Box>

                                </form>

                            </Grid>

                            <Grid item md={12} xs={12}>

                                <Button
                                    disabled={latlng == null}
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{mt: 3, mb: 2}}
                                >
                                    Sign up
                                </Button>

                            </Grid>

                            <Grid item md={12} xs={12}>

                                <Link href="/login" variant="body2">
                                    {"Already have an account? Login"}
                                </Link>

                            </Grid>

                        </Grid>

                    </Container>

                </Box>

            </Container>

        </ThemeProvider>
    );
}