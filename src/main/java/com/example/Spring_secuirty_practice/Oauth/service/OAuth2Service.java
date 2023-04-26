package com.example.Spring_secuirty_practice.Oauth.service;

import com.example.Spring_secuirty_practice.Oauth.Oauth.OAuthAttributes;
import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.domain.dto.UserDTO;
import com.example.Spring_secuirty_practice.Oauth.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final MemberRepository memberRepository;

    private HttpSession httpSession;



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        /*
        현재 로그인 진행중인 서비스를 구분하는 코드이다.
        지금은 구글만 사용하기 떄문에 없어도 되는 값이지만,
        이후 네이버 연동시에 네이버 로그인인지, 구글로그인인지 구분하기 위해 사용한다.
        * */

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();
        /*
        * OAuth2 로그인 진행 시 키가 되는 필드값을 이야기한다.
        *  Primary Key와 같은 의미이다. 구글의 경우는 기본적으로 코드를 지원하지만, 네이버 카카오등은 기본 지원하지 않는다.
        * 구글의 기본코드는 “sub”이다.
        * */
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        System.out.println("registrationId = " + registrationId);

        OAuthAttributes attributes  = OAuthAttributes.
                of(registrationId, userNameAttributeName,oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user",new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = memberRepository.findByEmail(attributes.toEntity().getEmail())
                .map(entity ->entity.update(attributes.toEntity().getUsername()))
                .orElse(attributes.toEntity());

        return memberRepository.save(user);
    }

}
