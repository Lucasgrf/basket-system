package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.Team;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        return adminService.getAllUser();
    }

    //Revisar o delete.
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id ,@RequestParam String confirmation){
        return adminService.deleteUser(id, confirmation);
    }

    @PostMapping("/users/add")
    public ResponseEntity<User> addUser(UserDTO body){
        return adminService.createUser(body);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO body){
        return adminService.updateUser(id,body);
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<Training>> getAllTrainings(){
        return adminService.getAllTrainings();
    }

    @DeleteMapping("/trainings/delete/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id, @RequestParam String confirm){
        return adminService.deleteTraining(id, confirm);
    }

    @PostMapping("/trainings/add")
    public ResponseEntity<Training> addTraining(TrainingDTO body){
        return adminService.createTraining(body);
    }

    @PutMapping("/trainings/update/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody TrainingDTO body){
        return adminService.updateTraining(id,body);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeam(){
        return adminService.getAllTeam();
    }

    @DeleteMapping("/teams/delete/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id, @RequestParam String confirmation){
        return adminService.deleteTeam(id, confirmation);
    }

    @PostMapping("/teams/add")
    public ResponseEntity<Team> addTeam(TeamDTO body){
        return adminService.createTeam(body);
    }

    @PutMapping("/teams/update/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody TeamDTO team){
        return adminService.updateTeam(id, team);
    }

    @GetMapping("/credentials")
    public ResponseEntity<List<Credential>> getAllCredential(){
        return adminService.getAllCredential();
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<Void> deleteCredential(@PathVariable Long id, @RequestParam String confirmation){
        return adminService.deleteCredential(id,confirmation);
    }

    @PostMapping("/credentials/add")
    public ResponseEntity<Credential> addCredential(@RequestBody CredentialDTO body,@RequestBody UserDTO userDTO){
        return adminService.createCredential(body, userDTO);
    }

    @PutMapping("/credentials/update/{id}")
    public ResponseEntity<Credential> updateCredential(@PathVariable Long id, @RequestBody CredentialDTO credential){
        return adminService.updateCredential(id, credential);
    }
}
