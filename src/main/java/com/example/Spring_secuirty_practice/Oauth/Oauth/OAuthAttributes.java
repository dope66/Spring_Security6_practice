package com.example.Spring_secuirty_practice.Oauth.Oauth;

import com.example.Spring_secuirty_practice.Oauth.Role.Role;
import com.example.Spring_secuirty_practice.Oauth.domain.User;
import lombok.Builder;

import java.util.Map;


/*
 * OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.
 * 네이버나 카카오등의 다른 소셜 로그인도 이 클래스를 사용한다.
 * */
public class OAuthAttributes {
    private Map<String , Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }
    /*
    * OAtuh2User가 반환하는 사용자 정보는 Map의 자료형으로 반환되기 때문에 값하나하나를 변환해주어야한다.
    * */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    /*
    * User 엔티티를 생성한다. OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할때 이며,
    *
    * 가입할때의 기본 권한을 GUEST로 주기 위해서 role값으로 Role.GUEST를 넘겨준다.
    * */
    public User toEntity(){
        return User.builder()
                .username(name)
                .email(email)
                .role(Role.USER)
                .build();
    }


}
