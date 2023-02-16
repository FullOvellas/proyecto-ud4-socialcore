package com.aad.proyectoud4socialcore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.maps.model.LatLng;
import lombok.*;

import javax.persistence.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class SocialUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String fullName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "residence_id")
    private Residence residence;
    @NonNull
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore // TODO: añadir valor por defecto porque puede ser un valor Null
    private URI profilePic;

    @JsonIgnore     // Para evitar recursión infinita en endpoints
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {
                    @JoinColumn(
                            name = "user_id", referencedColumnName = "id"
                    ),
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "role_id", referencedColumnName = "id"
                    ),
            }
    )
    private List<Role> roles;

    @JsonIgnore     // Para evitar recursión infinita en endpoints
    @ManyToMany(mappedBy = "participants")
    private List<UserGroup> groups;

    public SocialUser() {

        this.roles = new ArrayList<>();
        this.residence = new Residence(new LatLng(42.2404590, -8.6932391));

        this.residence.setCoordinates(new LatLng(13,12));

    }

    @Override
    public boolean equals(Object o) {

        if(o instanceof SocialUser ) {

            SocialUser user = (SocialUser) o;

            return user.getId().equals(this.getId());
        }

        return false;
    }

}
