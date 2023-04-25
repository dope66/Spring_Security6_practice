package com.example.Spring_secuirty_practice.Oauth.domain;

import com.example.Spring_secuirty_practice.Oauth.Role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Email
    private String email;
    @CreationTimestamp
    private Timestamp createDate;
    private Role role;

}
