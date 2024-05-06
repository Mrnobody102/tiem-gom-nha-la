package com.example.tiemgomnhala.config.security;

import com.example.tiemgomnhala.enums.UserEnum;
import com.example.tiemgomnhala.service.security.CustomOAuth2UserService;
import com.example.tiemgomnhala.service.security.CustomizeUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer{

    private CustomizeUserDetailsService customizeUserDetailsService;

    private CustomizeAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }


    @Autowired
    public SecurityConfig(CustomizeUserDetailsService customizeUserDetailsService, CustomizeAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomOAuth2UserService customOAuth2UserService) {
        this.customizeUserDetailsService = customizeUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable  csrf
        http.csrf(AbstractHttpConfigurer::disable);

//        // Authentication
        http.formLogin(auth -> auth
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login?error"));

        http.oauth2Login(auth -> auth
                .loginPage("/login")
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService)
			    )
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login?error"));

        http.logout(auth -> auth.logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout"));



//        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector).servletPath("/");

        // Authorization
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/ws/**", "/notifications/**", "/login", "/css/**", "/js/**", "/images/**", "/assets/**", "/oauth2/authorization/**").permitAll()
                        .requestMatchers("/admin/**", "/api/admin/**").hasAnyRole(UserEnum.Role.ADMIN.name())
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll());
        ;

        // Exception Handling
        http.exceptionHandling(auth -> auth.accessDeniedPage("/access_denied"));

        return http.build();
    }
}