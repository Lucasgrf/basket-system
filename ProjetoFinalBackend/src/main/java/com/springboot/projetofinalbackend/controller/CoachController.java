package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Team;
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
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String confirmation) {
        return teamService.delete(id, confirmation);
    }


    @PutMapping("/team/{id}")
    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody Team team) {
        return teamService.update(id, team);
    }

    //public boolean createTraining(Coach coach) {} OK
    //public boolean updateTraining(Coach coach) {} OK
    //public boolean deleteTraining(Coach coach) {} OK

    @PostMapping("/trainings/{trainingId}/players/{playerId}")
    public ResponseEntity<Set<Player>> addPlayer(@PathVariable Long trainingId, @PathVariable Long playerId) {
        try {
            Set<Player> updatedPlayers = trainingService.addPlayer(trainingId, playerId);
            return ResponseEntity.ok(updatedPlayers);
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
