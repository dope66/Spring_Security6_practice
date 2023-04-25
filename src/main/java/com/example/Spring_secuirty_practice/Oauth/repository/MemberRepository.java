package com.example.Spring_secuirty_practice.Oauth.repository;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
