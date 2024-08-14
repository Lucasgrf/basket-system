package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(User user) {
        return userService.login(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(User user){
        return userService.updateProfile(user);
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Void> deleteProfile(User user, String confirmation) {
        return userService.deleteProfile(user, confirmation);
    }

}
