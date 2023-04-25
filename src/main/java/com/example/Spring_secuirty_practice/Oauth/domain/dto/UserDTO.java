package com.example.Spring_secuirty_practice.Oauth.domain.dto;

import com.example.Spring_secuirty_practice.Oauth.Role.Role;
import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.passwordBean.PasswordBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @JsonProperty(value = "username")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;
    @JsonProperty(value = "password")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    @JsonProperty(value = "email")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    private String email;
    @Builder.Default
    private Role role = Role.USER; // 기본값으로 ROLE_USER 지정

    public User toEntity() {
        PasswordBean passwordBean = new PasswordBean();
        return User.builder().email(email)
                .username(username)
                .password(passwordBean.encodePassword(password))
                .role(role) // Role 값을 설정
                .build();
    }

}