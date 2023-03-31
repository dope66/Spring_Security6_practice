package com.example.Spring_secuirty_practice.Oauth.domain.dto;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.passwordBean.PasswordBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @JsonProperty(value = "username")
    @NotBlank
    private String username;
    @JsonProperty(value = "password")
    @NotBlank
    private String password;
    @JsonProperty(value = "email")
    @NotBlank
    @Email
    private String email;
    public User toEntity() {
        PasswordBean passwordBean = new PasswordBean();
        return User.builder().email(email)
                .username(username)
                .password(passwordBean.encodePassword(password))
                .role("USER")
                .build();
    }

}
