package com.example.Spring_secuirty_practice.Oauth.service;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> user = memberRepository.findByUsername(username);
        System.out.println("user = " + user);
        System.out.println("user is present() = "+user.isPresent());

        if (user.isPresent()) { // isPresent() 메소드 사용
            log.info("User found with username: {}", user.get().getUsername());

            return new UserDetail(user.get());
        } else {
            System.out.println("가입된 아이디가 아닙니다.");
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }
    public String getUserEmail(String username){
        Optional<User> user = memberRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get().getEmail();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
