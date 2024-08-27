package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CoachDTO;
import com.springboot.projetofinalbackend.model.Coach;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CoachRepository;
import com.springboot.projetofinalbackend.repository.TeamRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CoachService {
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CredentialService credentialService;

    public ResponseEntity<CoachDTO> createCoach(@RequestBody CoachDTO coach) {
        var existsCoach = coachRepository.findByNickname(coach.nickname());
        if (existsCoach.isEmpty()) {
            Coach newCoach = new Coach();
            newCoach.setNickname(coach.nickname());
            if (coach.userId() != null) {
                User user = userRepository.findById(coach.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                newCoach.setUser(user);
                coachRepository.save(newCoach);
                return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(newCoach));
            }
            User user = generateRandomUser();
            newCoach.setUser(user);
            coachRepository.save(newCoach);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(newCoach));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<CoachDTO> getCoach(@PathVariable Long id) {
        Optional<Coach> coach = coachRepository.findById(id);
        if(coach.isPresent()) {
            Coach coachAux = coach.get();
            return ResponseEntity.status(HttpStatus.OK).body(toDTO(coachAux));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<CoachDTO>> getAllCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        List<CoachDTO> coachDTOs = coaches.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(coachDTOs);
    }

    public ResponseEntity<CoachDTO> updateCoach(@PathVariable Long id, @RequestBody CoachDTO updatedCoach) {
        var existsCoach = coachRepository.findById(id);
        if (existsCoach.isPresent()) {
            Coach coach = existsCoach.get();

            if (updatedCoach.nickname() != null) {
                coach.setNickname(updatedCoach.nickname());
            }
            if (updatedCoach.userId() != null) {
                userRepository.findById(updatedCoach.userId()).ifPresent(coach::setUser);
            }

            coachRepository.save(coach);
            return ResponseEntity.status(HttpStatus.OK).body(toDTO(coach));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Void> deleteCoach(Long id) {
        if (coachRepository.existsById(id)) {
            coachRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found");
        }
    }

    public CoachDTO toDTO(Coach coach) {
        return new CoachDTO(
                coach.getId(),
                coach.getNickname() != null ? coach.getNickname() : "",
                coach.getUser().getId(),
                coach.getTeam() != null ? coach.getTeam().getId() : null
        );
    }

    public User generateRandomUser(){
        User user = new User();

        List<String> nomes = Arrays.asList("João", "Pedro", "Carlos", "Lucas", "Miguel", "Gabriel", "Rafael", "Felipe", "Gustavo");


        Random random = new Random();
        int randomNumber = random.nextInt(100);
        String randomName = nomes.get(random.nextInt(nomes.size())) + random.nextInt(100);

        String email = randomName + randomNumber + "@gmail.com";
        user.setEmail(email);

        user.setUsername(randomName);
        user.setPhotoName("");
        user.setPassword(passwordEncoder.encode("padrao@user" + randomName));
        user.setRole(User.Role.COACH);
        userRepository.save(user);
        Credential credential = credentialService.create(user);
        user.setCredential(credential);
        userRepository.save(user);
        return user;
    }
}
