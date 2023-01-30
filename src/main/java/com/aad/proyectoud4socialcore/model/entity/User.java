package com.aad.proyectoud4socialcore.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String fullName;
    @NonNull
    private String username;
    private URI profilePic;

}
