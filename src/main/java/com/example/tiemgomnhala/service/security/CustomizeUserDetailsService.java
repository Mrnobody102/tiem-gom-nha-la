package com.example.tiemgomnhala.service.security;

import com.example.tiemgomnhala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("customizeUserDetailService")
public class CustomizeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomizeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.tiemgomnhala.entity.User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

//         Granted Authority
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
    }
}
