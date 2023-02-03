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

    public UserDTO(String fullName, String email, String password ) {

        this.fullName = fullName;
        this.email = email;
        this.password = password;

    }
    public UserDTO() {

    }

}
