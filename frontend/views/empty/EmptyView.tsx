import {useEffect, useLayoutEffect, useState} from "react";
import {UserAuthEndpoint} from "Frontend/generated/endpoints";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

export default function EmptyView() {

    const [user, setUser] = useState<String>("");

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
      <Box>
          <Typography variant={"h2"} textAlign={"center"}>Home page</Typography>
          <Typography variant={"h4"}>Usuario: {user}</Typography>
      </Box>
  );
}
