package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.*;
import com.springboot.projetofinalbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final PlayerRepository playerRepository;
    private final CredentialService credentialService;
    private final UserService userService;
    private final TrainingRepository trainingRepository;
    private final TrainingService trainingService;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final CredentialRepository credentialRepository;

    public ResponseEntity<User> createUser(@RequestBody UserDTO body) {
        var existingUser = userRepository.findByEmail(body.email());
        if(existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUsername(body.username());
            newUser.setRole(body.role());
            newUser.setPhotoName(body.photoName());

            switch (body.role()) {
                case COACH -> {
                    Coach coach = new Coach();
                    coach.setUser(newUser);
                    coachRepository.save(coach);
                }
                case PLAYER -> {
                    Player player = new Player();
                    player.setUser(newUser);
                    playerRepository.save(player);
                }
                case ADMIN -> {
                    Admin admin = new Admin();
                    admin.setUser(newUser);
                    adminRepository.save(admin);
                }
                default -> throw new IllegalStateException("Role not found: " + body.role());
            }

            userRepository.save(newUser);
            credentialService.create(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    public ResponseEntity<Void> deleteUser(@PathVariable Long id ,@RequestParam String confirmation) {
        return userService.deleteProfile(id, confirmation);
    }

    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO body) {
        var userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userUpdate.setUsername(body.username());
        userUpdate.setPassword(passwordEncoder.encode(body.password()));
        userUpdate.setRole(body.role());
        userUpdate.setPhotoName(body.photoName());
        userUpdate.setEmail(body.email());

        userRepository.save(userUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);

    }

    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(trainings);
    }

    public ResponseEntity<Void> deleteTraining(@PathVariable Long trainingId, @RequestParam String confirm) {
        return trainingService.delete(trainingId, confirm);
    }

    public ResponseEntity<Training> createTraining(TrainingDTO body) {
        return trainingService.create(body);
    }

    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody TrainingDTO body) {
        return trainingService.update(id,body);
    }

    public ResponseEntity<List<Team>> getAllTeam() {
        List<Team> teams = teamRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    public ResponseEntity<Void> deleteTeam(@PathVariable Long id, @RequestParam String confirmation) {
        return teamService.delete(id,confirmation);
    }

    public ResponseEntity<Team> createTeam(TeamDTO body) {
        return teamService.create(body);
    }

    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody TeamDTO team) {
        return teamService.update(id,team);
    }

    public ResponseEntity<List<Credential>> getAllCredential() {
        List<Credential> credentials = credentialRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(credentials);
    }

    public ResponseEntity<Void> deleteCredential(@PathVariable Long id, @RequestParam String confirmation) {
        return credentialService.delete(id,confirmation);
    }


    public ResponseEntity<Credential> createCredential(CredentialDTO body, UserDTO userDTO) {
            var existsCredential = credentialRepository.findByName(body.name());
            var existsUser = userRepository.findByEmail(userDTO.email());
            if(existsCredential.isEmpty() && existsUser.isEmpty()) {
                var user = new User();
                var credential = new Credential();
                BeanUtils.copyProperties(userDTO, user);
                credential.setUser(user);
                credential.setPhotoName(null);
                credential.setName(user.getUsername());
                credential.setTeamId(null);
                credential.setUserType(user.getRole().name());
                credentialRepository.save(credential);
                return ResponseEntity.status(HttpStatus.CREATED).body(credential);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public ResponseEntity<Credential> updateCredential(@PathVariable Long id, @RequestBody CredentialDTO credential) {
        return credentialService.update(id,credential);
    }
}
