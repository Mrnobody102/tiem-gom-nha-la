package com.example.tiemgomnhala.controller;

import com.example.tiemgomnhala.dto.request.UserCreationRequest;
import com.example.tiemgomnhala.entity.User;
import com.example.tiemgomnhala.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    List<User> getUsers(){

        return null;
    }
}
