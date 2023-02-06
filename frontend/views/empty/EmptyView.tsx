import React, {useLayoutEffect, useState} from "react";
import {UserAuthEndpoint} from "Frontend/generated/endpoints";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Menu from "@mui/material/Menu";
import MenuItem from '@mui/material/MenuItem';
import {AppBar, IconButton, Toolbar} from "@mui/material";

import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import {Padding} from "@mui/icons-material";
import Button from "@mui/material/Button";
import {createTheme} from "@mui/material/styles";
import {useNavigate} from "react-router-dom";
import SocialAppBar from "Frontend/components/SocialAppBar";

export default function EmptyView() {

  return (
      <Box>
          <SocialAppBar/>
          <Typography variant={"h2"} textAlign={"center"}>Home page</Typography>
      </Box>
  );
}
