package com.aad.proyectoud4socialcore.model.entity;

import dev.hilla.Nonnull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Nonnull
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

    @Override
    public boolean equals(Object o ) {

        if(!(o instanceof UserGroup )) {
            return false;
        }

        UserGroup group = (UserGroup) o;

        if(group.getId().equals(getId()) ) {
            return true;
        }

        return false;
    }

}
