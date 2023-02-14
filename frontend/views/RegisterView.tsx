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
import {GoogleMap, useLoadScript} from "@react-google-maps/api";
import Placeholder from "Frontend/components/placeholder/Placeholder";
import {List} from "@mui/icons-material";

export default function RegisterView() {

    const theme = createTheme();

    const [email, setEmail] = useState<string>("");
    const [fullName, setFullName] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [passwordMatch, setPasswordMatch] = useState<string>("");

    const [activeStep, setActiveStep] = useState<number>(0);
    const [error, setError] = useState<string>("")
    const [searchParams, setSearchParams] = useSearchParams();

    const {isLoaded} = useLoadScript({
        googleMapsApiKey: "AIzaSyDenRxQ8_1INjqF9cvWTejzSrgo7lsHYtQ"
    })

    function handleNext() {

        if (email == "" || fullName == "" || password == "" || passwordMatch == "") {
            setError("Fields can't be empty")
            return
        }

        if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email!.toString())) {
            setError("Email is not valid")
            return;
        }

        if (password != passwordMatch) {
            setError("Passwords don't match");
            return;
        }

        if (password!.length < 5) {
            setError("Password must have at least 5 characters");
            return;
        }

        setError("");

        setActiveStep(1);

    }

    function handlePrevious() {

        setActiveStep(0);

    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const data = new FormData(event.currentTarget);

        if (email == "" || fullName == "" || password == "" || passwordMatch == "") {
            setError("Fields can't be empty")
            return
        }

        if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email!.toString())) {
            setError("Email is not valid")
            return;
        }

        if (password != passwordMatch) {
            setError("Passwords don't match");
            return;
        }

        if (password!.length < 5) {
            setError("Password must have at least 5 characters");
            return;
        }

        setError("");

        fetch("/register", {
            method: "POST",
            body: new URLSearchParams([["email", email!.toString()], ["fullName", fullName!.toString()], ["password", password!.toString()]])
        }).then(v => {
            if (v.redirected) window.location.replace(v.url);
        }).catch(e => console.log("Error"))

    }

    useEffect(() => {

        if (searchParams.get("error") != null) {

            setError("Invalid credentials")

        }

    });

    function UserLocationStep() {
        return (
          <>

              {!isLoaded &&
                  <Placeholder/>
              }

              {isLoaded &&
                  <GoogleMap/>
              }

          </>
        );
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

                                    <Box sx={{ width: "80%", margin: "auto" }}>

                                        <Stepper dir={"ltr"} activeStep={activeStep} orientation={"vertical"}>

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

                                                <UserLocationStep/>

                                            </Step>

                                        </Stepper>

                                    </Box>

                                </form>

                            </Grid>

                            <Grid item md={12} xs={12}>

                                <Button
                                    disabled={true}
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