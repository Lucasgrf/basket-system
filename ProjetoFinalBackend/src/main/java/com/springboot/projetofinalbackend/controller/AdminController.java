package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    //public boolean deleteUser(){};
    //public boolean addUser(){};
    //public boolean updateUser(){};
    //public boolean getAllTrainings(){};
    //public boolean deleteTraining(){};
    //public boolean addTraining(){};
    //public boolean updateTraining(){};
    //public boolean getAllTeam(){};
    //public boolean deleteTeam(){};
    //public boolean addTeam(){};
    //public boolean updateTeam(){};
    //public boolean getAllCredential(){};
    //public boolean deleteCredential(){};
    //public boolean addCredential(){};
    //public boolean updateCredential(){};
}
