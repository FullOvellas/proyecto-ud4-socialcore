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
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {useState} from "react";


export default function RegisterView(){

    const theme = createTheme();

    const [error, setError] = useState<String>("")

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const data = new FormData(event.currentTarget);

        const fullName = data.get('fullName')
        const email = data.get('email');
        const password = data.get('password');
        const passwordRep = data.get('password_rep')

        if(email == "" || fullName == "" || password == "" || passwordRep == "" ) {
            setError("Los campos no pueden estar vacíos")
            return
        }

        if(password != passwordRep) {
            setError("Las contraseñas no coinciden");
            return;
        }

        fetch("/register", {
            method: "POST",
            body: new URLSearchParams([["email", email!.toString()], ["fullName", fullName!.toString()], ["password", password!.toString()]])
        }).then(v => {
            if(v.redirected) window.location.replace(v.url);
        }).catch(e => console.log("Erro2r"))

    }

    return(
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
                <Typography component="h1" variant="h5">
                    Registro
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="fullName"
                        label="Nombre"
                        name="fullName"
                        autoComplete="name"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Correo electrónico"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Contraseña"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password_rep"
                        label="Repite la contraseña"
                        type="password"
                        id="password_rep"
                        autoComplete="current-password"
                    />
                    {error != "" &&
                        <Typography color={"#FF0000"}>{error}</Typography>
                    }
                    <FormControlLabel
                        control={<Checkbox value="remember" color="primary" />}
                        label="Recordarme"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Iniciar sesión
                    </Button>
                    <Grid container>
                        <Grid item>
                            <Link href="/login" variant="body2">
                                {"¿Tienes cuenta? Inicia sesión"}
                            </Link>
                        </Grid>
                    </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}