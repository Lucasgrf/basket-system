package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.CoachDTO;
import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.model.Coach;
import com.springboot.projetofinalbackend.service.CoachService;
import com.springboot.projetofinalbackend.service.TeamService;
import com.springboot.projetofinalbackend.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/coach")
@PreAuthorize("hasAnyRole('ADMIN','COACH')")
public class CoachController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CoachService coachService;

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @PostMapping("/team")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamDTO team) {
        return teamService.create(team);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @DeleteMapping("/team/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        return teamService.delete(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @PutMapping("/team/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamDTO team) {
        return teamService.update(id, team);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @PostMapping("/training")
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody TrainingDTO trainingDTO) {
        return trainingService.create(trainingDTO);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @PutMapping("/training/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id ,@RequestBody TrainingDTO trainingDTO) {
        return trainingService.update(id,trainingDTO);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @DeleteMapping("/training/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        return trainingService.delete(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @PostMapping("/training/{trainingId}/player/{playerId}")
    public ResponseEntity<Set<PlayerDTO>> addPlayer(@PathVariable Long trainingId, @PathVariable Long playerId) {
        try {
            Set<PlayerDTO> updatedPlayers = trainingService.addPlayer(trainingId, playerId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPlayers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //@PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @DeleteMapping("/training/{trainingId}/player/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long trainingId, @PathVariable Long playerId) {
        try {
            return trainingService.deletePlayer(trainingId, playerId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CoachDTO>> getAllCoaches(){
        return coachService.getAllCoaches();
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<CoachDTO> createCoach(@RequestBody @Valid CoachDTO coachDTO) {
        return coachService.createCoach(coachDTO);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id) {
        return coachService.getCoach(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        return coachService.deleteCoach(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CoachDTO> updateCoach(@PathVariable Long id, @RequestBody CoachDTO coachDTO) {
        return coachService.updateCoach(id,coachDTO);
    }
}
