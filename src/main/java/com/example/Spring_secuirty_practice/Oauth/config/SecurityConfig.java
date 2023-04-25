package com.example.Spring_secuirty_practice.Oauth.config;

import com.example.Spring_secuirty_practice.Oauth.service.CustomUserDetailsService;
import com.example.Spring_secuirty_practice.Oauth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    private final OAuth2Service oAuth2Service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/member/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                .failureHandler(new CustomAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/")// 로그인 성공시 이동할 URL
                .userInfoEndpoint()// 사용자가 로그인에 성공하였을 경우,
                .userService(oAuth2Service)
                .and()
                .and()
                .build();



    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
        };
    }
}
