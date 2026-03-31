package com.apps.quantitymeasurement.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String password;

    private String phone;

    private String bio;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    public enum AuthProvider {
        LOCAL, GOOGLE
    }

    public UserEntity(String email, String name, String password, AuthProvider provider) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.provider = provider;
    }
}
