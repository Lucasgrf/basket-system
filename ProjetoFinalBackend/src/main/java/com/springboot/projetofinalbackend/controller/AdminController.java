package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.*;
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
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return adminService.getAllUser();
    }

    //Revisar o delete.
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody RequestDeleteDTO body){
        return adminService.deleteUser(id,body);
    }

    @PostMapping("/users/add")
    public ResponseEntity<User> addUser(@RequestBody UserDTO user, @RequestParam String password){
        return adminService.createUser(user,password);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody ResponseUpdateUser user){
        return adminService.updateUser(id,user);
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingDTO>> getAllTrainings(){
        return adminService.getAllTrainings();
    }

    @DeleteMapping("/trainings/delete/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id, @RequestParam String title){
        return adminService.deleteTraining(id,title);
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
    public ResponseEntity<List<TeamDTO>> getAllTeam(){
        return adminService.getAllTeams();
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
    public ResponseEntity<List<CredentialDTO>> getAllCredential(){
        return adminService.getAllCredentials();
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

    @GetMapping("/users/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        return adminService.getAllPlayers();
    }

    @GetMapping("/users/coaches")
    public ResponseEntity<List<CoachDTO>> getAllCoaches(){
        return adminService.getAllCoaches();
    }
}
