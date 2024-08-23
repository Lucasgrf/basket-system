package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.RequestConfirmDTO;
import com.springboot.projetofinalbackend.DTO.RequestUpdateUser;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.service.AdminService;
import com.springboot.projetofinalbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ADMIN','COACH','PLAYER')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAnyRole('ADMIN','COACH','PLAYER')")
    @PutMapping("/profile/{id}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id, @RequestBody RequestConfirmDTO user){
        return userService.updateProfile(id,user);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COACH','PLAYER')")
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id, @RequestBody RequestConfirmDTO body) {
        return userService.deleteProfile(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COACH','PLAYER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        return userService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return adminService.getAllUser();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody RequestConfirmDTO body){
        return adminService.deleteUser(id,body);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO user, @RequestParam String password){
        return adminService.createUser(user,password);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody RequestUpdateUser user){
        return adminService.updateUser(id,user);
    }
}
