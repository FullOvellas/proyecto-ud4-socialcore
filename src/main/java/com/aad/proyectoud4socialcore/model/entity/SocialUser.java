package com.aad.proyectoud4socialcore.model.entity;

import lombok.*;

import javax.persistence.*;
import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
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

    @ElementCollection
    private List<String> roles;

}
