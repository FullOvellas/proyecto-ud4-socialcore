import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import MeetingMap from "Frontend/components/MeetingMap"
import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";
import Residence from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Residence";
import PointOfInterest from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/PointOfInterest"
import {useNavigate, useSearchParams} from "react-router-dom";
import {useState} from "react";
import {MeetingEndpoint} from "Frontend/generated/endpoints";

export default function MeetingView() {

    const navigate = useNavigate()

    const [userMail, setUserMail] = useState<string>("");
    const [searchParams, setSearchParams] = useSearchParams();
    const [showModal, setShowModal] = useState<boolean>(false);
    const [isCreator, setIsCreator] = useState<boolean>(false);
    const [error, setError] = useState<String>("");
    const [meeting, setMeeting] = useState<Meeting>();

    const theme = createTheme();


    if (searchParams.get("meeting_id") !== null) {

        const meeting: Meeting = MeetingEndpoint.findById()

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
                    { meeting != null &&
                        <MeetingMap meeting={meeting} />
                    }
                </Box>
            </Container>
        </ThemeProvider>
    );
}
