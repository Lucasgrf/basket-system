package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.*;
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

import java.util.List;

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

    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user, @RequestParam String password) {
        var existingUser = userRepository.findByEmail(user.email());
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setEmail(user.email());
            newUser.setUsername(user.username());
            newUser.setRole(user.role());
            newUser.setPhotoName(user.photoName());
            userRepository.save(newUser);

            switch (user.role()) {
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
                default -> throw new IllegalStateException("Role not found: " + user.role());
            }
            credentialService.create(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(newUser));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        return userService.deleteProfile(userId);
    }

    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO body) {
        var existsUser = userRepository.findById(id);
        if(existsUser.isPresent()) {
            User userUpdate = existsUser.get();
            userUpdate.setUsername(body.username());
            userUpdate.setPhotoName(body.photoName());
            userUpdate.setEmail(body.email());
            userUpdate.setRole(body.role());
            userRepository.save(userUpdate);
            return ResponseEntity.ok(toDTO(userUpdate));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll();
        List<TrainingDTO> trainingDTOs = trainings.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(trainingDTOs);
    }

    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        return trainingService.delete(id);
    }

    public ResponseEntity<TrainingDTO> createTraining(TrainingDTO body) {
        return trainingService.create(body);
    }

    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id, @RequestBody TrainingDTO body) {
        return trainingService.update(id, body);
    }

    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> teamDTOs = teams.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(teamDTOs);
    }

    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        return teamService.delete(id);
    }

    public ResponseEntity<TeamDTO> createTeam(TeamDTO body) {
        return teamService.create(body);
    }

    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamDTO team) {
        return teamService.update(id, team);
    }

    public ResponseEntity<List<CredentialDTO>> getAllCredentials() {
        List<Credential> credentials = credentialRepository.findAll();
        List<CredentialDTO> credentialDTOs = credentials.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(credentialDTOs);
    }

    public ResponseEntity<Void> deleteCredential(@PathVariable Long id) {
        return credentialService.delete(id);
    }

    public ResponseEntity<CredentialDTO> updateCredential(@PathVariable Long id, @RequestBody CredentialDTO credential) {
        return credentialService.update(id, credential);
    }

    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDTO> playerDTOs = players.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(playerDTOs);
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhotoName(),
                user.getRole(),
                user.getPlayer() != null ? user.getPlayer().getId() : null,
                user.getCoach() != null ? user.getCoach().getId() : null,
                user.getCredential() != null ? user.getCredential().getId() : null
        );
    }

    public TeamDTO toDTO(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getAddress(),
                team.getGym(),
                team.getFoundation(),
                team.getEmailContact(),
                team.getPhoneContact(),
                team.getCoach() != null ? team.getCoach().getId() : null
        );
    }

    public PlayerDTO toDTO(Player player) {
        return new PlayerDTO(
                player.getId(),
                player.getNickname(),
                player.getUser().getId(),
                player.getPosition(),
                player.getHeight(),
                player.getWeight(),
                player.getAge(),
                player.getTeam() != null ? player.getTeam().getId() : null
        );
    }

    public TrainingDTO toDTO(Training training) {
        return new TrainingDTO(
                training.getId(),
                training.getTitle(),
                training.getDate(),
                training.getLocation(),
                training.getTeam() != null ? training.getTeam().getId() : null
        );
    }

    public CredentialDTO toDTO(Credential credential) {
        return new CredentialDTO(
                credential.getId(),
                credential.getPhotoName(),
                credential.getName(),
                credential.getTeamId(),
                credential.getUserType(),
                credential.getUser() != null ? credential.getUser().getId() : null
        );
    }
}
