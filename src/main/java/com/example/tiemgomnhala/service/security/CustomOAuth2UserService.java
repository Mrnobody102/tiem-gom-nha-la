package com.example.tiemgomnhala.service.security;

import com.example.tiemgomnhala.entity.User;
import com.example.tiemgomnhala.entity.CustomOAuth2User;
import com.example.tiemgomnhala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oauthUser =  super.loadUser(userRequest);
        String email = oauthUser.<String>getAttribute("email");
        User user = userRepository.findByEmail(email).orElse(null);
        // Granted Authority
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        OAuth2User oAuth2User = new CustomOAuth2User(oauthUser, clientName, authorities);
        return oAuth2User;
    }

}