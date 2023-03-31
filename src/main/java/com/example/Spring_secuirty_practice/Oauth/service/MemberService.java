package com.example.Spring_secuirty_practice.Oauth.service;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.domain.dto.UserDTO;
import com.example.Spring_secuirty_practice.Oauth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public User doJoin(UserDTO userDTO)throws Exception {

//        newUser.setUsername(userDTO.getUsername());
//        newUser.setPassword(userDTO.getPassword());
//        newUser.setEmail(userDTO.getEmail());
        return memberRepository.save(userDTO.toEntity());
    }


}
