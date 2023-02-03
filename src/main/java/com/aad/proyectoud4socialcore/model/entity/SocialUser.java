package com.aad.proyectoud4socialcore.model.entity;

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
    @NonNull
    private String email;

    private String password;
    private URI profilePic;

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

    @ManyToMany(mappedBy = "participants")
    private List<UserGroup> groups;

    public SocialUser() {

        this.roles = new ArrayList<>();

    }

}
