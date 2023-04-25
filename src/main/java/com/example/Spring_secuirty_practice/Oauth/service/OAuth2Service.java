package com.example.Spring_secuirty_practice.Oauth.service;

import com.example.Spring_secuirty_practice.Oauth.domain.dto.UserDTO;
import com.example.Spring_secuirty_practice.Oauth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인을 수행한 서비스의 이름

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //PK 가 되는 정보


        Map<String, Object> attributes = oAuth2User.getAttributes(); // 사용자가 가지고 있는 정보
//        UserDTO userDTO = OAuthAttributes.extract(registrationId, attributes);
        return null;
    }
}
