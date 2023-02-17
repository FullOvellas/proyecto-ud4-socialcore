package com.aad.proyectoud4socialcore.model.dto;

import lombok.Data;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Transactional
@Data
public class UserDTO {

    @NotNull
    @NotEmpty
    private String fullName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private double lng;

    @NotNull
    @NotEmpty
    private double lat;

    public UserDTO(String fullName, String email, String password, double lat, double lng) {

        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.lat = lat;
        this.lng = lng;

    }
    public UserDTO() {

    }

}
