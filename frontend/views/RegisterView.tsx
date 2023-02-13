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
import {Step, StepLabel, Stepper} from "@mui/material";

export default function RegisterView() {

    const theme = createTheme();

    const [error, setError] = useState<string>("")
    const [searchParams, setSearchParams] = useSearchParams();

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const data = new FormData(event.currentTarget);

        const fullName = data.get('fullName')
        const email = data.get('email');
        const password = data.get('password');
        const passwordRep = data.get('password_rep')

        if (email == "" || fullName == "" || password == "" || passwordRep == "") {
            setError("Fields can't be empty")
            return
        }

        if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email!.toString())) {
            setError("Email is not valid")
            return;
        }

        if (password != passwordRep) {
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

                                <Stepper activeStep={0}>

                                    <Step disabled={true}>

                                        <StepLabel>Credentials</StepLabel>

                                        <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                                            <TextField
                                                margin="normal"
                                                required
                                                fullWidth
                                                id="fullName"
                                                label="Name"
                                                name="fullName"
                                                autoComplete="name"
                                                autoFocus
                                            />
                                            <TextField
                                                margin="normal"
                                                required
                                                fullWidth
                                                id="email"
                                                label="Email"
                                                name="email"
                                                autoComplete="email"
                                                autoFocus
                                            />
                                            <TextField
                                                margin="normal"
                                                required
                                                fullWidth
                                                name="password"
                                                label="Password"
                                                type="password"
                                                id="password"
                                                autoComplete="current-password"
                                            />
                                            <TextField
                                                margin="normal"
                                                required
                                                fullWidth
                                                name="password_rep"
                                                label="Repeat password"
                                                type="password"
                                                id="password_rep"
                                                autoComplete="current-password"
                                            />
                                            <Typography variant={"caption"} color={"rgb(155,155,155)"}>* Password must have
                                                at least 5 characters</Typography>
                                            {error != "" &&
                                                <Typography color={"#FF0000"}>{error}</Typography>
                                            }
                                            <Button
                                                type="submit"
                                                fullWidth
                                                variant="contained"
                                                sx={{mt: 3, mb: 2}}
                                            >
                                                Sign up
                                            </Button>
                                            <Grid container>
                                                <Grid item>
                                                    <Link href="/login" variant="body2">
                                                        {"Already have an account? Login"}
                                                    </Link>
                                                </Grid>
                                            </Grid>
                                        </Box>

                                    </Step>

                                    <Step>
                                        <StepLabel>User location</StepLabel>
                                    </Step>

                                </Stepper>

                            </Grid>

                        </Grid>

                    </Container>

                </Box>
            </Container>
        </ThemeProvider>
    );
}