package com.aad.proyectoud4socialcore.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private SocialUser creator;

    @ManyToMany()
    @JoinTable(name = "users_groups",
            joinColumns = {
                @JoinColumn(name = "id_group", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "id_user", referencedColumnName = "id")
            }
    )
    private List<SocialUser> participants;

    public UserGroup() {

        participants = new ArrayList<>();

    }

}
