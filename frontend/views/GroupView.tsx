import {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import SocialAppBar from "Frontend/components/SocialAppBar";
import UserGroup from "Frontend/generated/com/aad/proyectoud4socialcore/model/entity/UserGroup";
import {UserGroupEndpoint} from "Frontend/generated/endpoints";

export default function GroupView() {

    const [groups, setGroups] = useState<UserGroup[]>()

    const loadUserGroups = async () => {
        const groups = await UserGroupEndpoint.getUserGroups();
        setGroups(groups)
    }

    useEffect(() => {
        loadUserGroups()
    }, []);

    return(

        <Box>
            <SocialAppBar/>
        </Box>

    );

}