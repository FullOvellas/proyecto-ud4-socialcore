import {Status, Wrapper} from "@googlemaps/react-wrapper";
import CircularProgress from '@mui/material/CircularProgress';
import {Alert} from "@mui/material";
import MeetingMap from "components/MeetingMap"
import Meeting from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/Meeting";
import SocialUser from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/SocialUser";


let user: SocialUser = {
    fullName: "a",
    email: "a@a.com"
}

let meeting: Meeting = {
    attendants: [
        user
    ],
    maxRadiusMeters: 50000
}

export default function MeetingView() {

    const apiKey: string = process.env.MAPS_API_KEY === undefined ?
        '' : process.env.MAPS_API_KEY;

    return <Wrapper apiKey={apiKey} render={render} />

}


const render = (status: Status) => {

    switch (status) {

        case Status.LOADING:
            return <CircularProgress />;

        case Status.FAILURE:
            return <Alert severity="error">Can not load map</Alert>;

        case Status.SUCCESS:
            return <MeetingMap
                maxRadiusMeters={meeting.maxRadiusMeters}
                attendants={meeting.attendants}
            />
    }
};
