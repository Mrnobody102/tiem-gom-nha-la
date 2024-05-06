package com.example.tiemgomnhala.service;

import com.example.tiemgomnhala.dto.request.UserCreationRequest;
import com.example.tiemgomnhala.entity.User;
import com.example.tiemgomnhala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreationRequest request){
        return null;
    }


}
