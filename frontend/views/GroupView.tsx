import {useState} from "react";
import Box from "@mui/material/Box";
import SocialAppBar from "Frontend/components/SocialAppBar";

export default function GroupView() {

    const [groups, setGroups] = useState<String[]>()

    return(

        <Box>
            <SocialAppBar/>
        </Box>

    );

}