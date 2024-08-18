package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Team;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.repository.TrainingRepository;
import com.springboot.projetofinalbackend.service.TeamService;
import com.springboot.projetofinalbackend.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/coach")
public class CoachController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TeamService teamService;

    @PostMapping("/team")
    public ResponseEntity<Team> createTeam(@RequestBody @Valid TeamDTO team) {
        return teamService.create(team);
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id, @RequestParam String confirmation) {
        return teamService.delete(id, confirmation);
    }


    @PutMapping("/team/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody TeamDTO team) {
        return teamService.update(id, team);
    }

    @PostMapping("/training")
    public ResponseEntity<Training> createTraining(@RequestBody TrainingDTO trainingDTO) {
        return trainingService.create(trainingDTO);
    }

    @PutMapping("/training/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id ,@RequestBody TrainingDTO trainingDTO) {
        return trainingService.update(id,trainingDTO);
    }

    @DeleteMapping("/training/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id, @RequestParam String confirmation) {
        return trainingService.delete(id,confirmation);
    }

    @PostMapping("/trainings/{trainingId}/players/{playerId}")
    public ResponseEntity<Set<Player>> addPlayer(@PathVariable Long trainingId, @PathVariable Long playerId) {
        try {
            Set<Player> updatedPlayers = trainingService.addPlayer(trainingId, playerId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPlayers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/trainings/{trainingId}/players/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long trainingId, @PathVariable Long playerId) {
        try {
            return trainingService.deletePlayer(trainingId, playerId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
