package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CoachDTO;
import com.springboot.projetofinalbackend.model.Coach;
import com.springboot.projetofinalbackend.repository.CoachRepository;
import com.springboot.projetofinalbackend.repository.TeamRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<CoachDTO> createCoach(CoachDTO coach) {
        Optional<Coach> existsCoach = coachRepository.findById(coach.id());
        if (existsCoach.isEmpty()) {
            Coach newCoach = new Coach();
            newCoach.setTeam(teamRepository.findById(coach.teamId()).orElse(null));
            newCoach.setUser(userRepository.findById(coach.userId()).orElse(null));
            Coach savedCoach = coachRepository.save(newCoach);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(savedCoach));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<CoachDTO> getCoach(Long id) {
        Optional<Coach> coach = coachRepository.findById(id);
        if(coach.isPresent()) {
            Coach coachAux = coach.get();
            coachRepository.findById(coachAux.getId());
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

    public ResponseEntity<CoachDTO> updateCoach(Long id, CoachDTO updatedCoach) {
        var existsCoach = coachRepository.findById(id);
        if(existsCoach.isPresent()) {
            Coach coachAux = existsCoach.get();
            coachAux.setTeam(teamRepository.findById(updatedCoach.teamId()).orElse(null));
            coachAux.setUser(userRepository.findById(updatedCoach.userId()).orElse(null));
            coachRepository.save(coachAux);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
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
                coach.getUser() != null ? coach.getUser().getId() : null,
                coach.getTeam() != null ? coach.getTeam().getId() : null
        );
    }
}
