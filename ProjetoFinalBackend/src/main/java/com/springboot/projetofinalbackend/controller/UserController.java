package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.RequestDeleteDTO;
import com.springboot.projetofinalbackend.DTO.RequestUpdateProfileDTO;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.service.AdminService;
import com.springboot.projetofinalbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PutMapping("/profile/{id}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id, @RequestBody RequestUpdateProfileDTO user){
        return userService.updateProfile(id,user);
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id, @RequestBody RequestDeleteDTO body) {
        return userService.deleteProfile(id, body);
    }

}
