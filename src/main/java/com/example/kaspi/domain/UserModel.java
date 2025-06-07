package com.example.kaspi.domain;



import com.example.kaspi.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserModel {
    @Id @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;  // хранить в хешированном виде

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private RoleEnum role;

    @Column(nullable = false)
    private boolean enabled;
}

