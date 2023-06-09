package com.example.Spring_secuirty_practice.Oauth.service;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.domain.dto.UserDTO;
import com.example.Spring_secuirty_practice.Oauth.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public User doJoin(UserDTO userDTO)throws Exception {
        return memberRepository.save(userDTO.toEntity());
    }
    public String getUsername(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println("authentication = " + authentication);
        return authentication.getName();
    }
    public String getEmaiL(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Optional<User> user = memberRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get().getEmail();
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

//     회원 가입 시 , 유효성 체크

    public Map<String,String> validateHandling(Errors errors){
        Map<String,String> validatorResult=new HashMap<>();
//        유효성 검사 실패 시 필드 목록을 받음
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s",error.getField());
            validatorResult.put(validKeyName,error.getDefaultMessage());
        }
        return validatorResult;
    }
    public boolean isDuplicateId(String username){
        return memberRepository.findByUsername(username).isPresent();
    }
}
