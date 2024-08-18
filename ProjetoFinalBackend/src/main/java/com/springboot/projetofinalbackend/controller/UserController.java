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

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody @Valid UserDTO user){
        return userService.updateProfile(user);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile(@RequestBody @Valid UserDTO user) {
        return userService.deleteProfile(user);
    }

}
