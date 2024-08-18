package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.Admin;
import com.springboot.projetofinalbackend.model.Coach;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.AdminRepository;
import com.springboot.projetofinalbackend.repository.CoachRepository;
import com.springboot.projetofinalbackend.repository.PlayerRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final PlayerRepository playerRepository;
    private final CredentialService credentialService;

    public ResponseEntity createUser(@RequestBody UserDTO body) {
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

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /*public ResponseEntity updateUser(@RequestBody AdminDTO body) {
        var existingAdmin = adminRepository.findByEmail(body.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }*/
}
