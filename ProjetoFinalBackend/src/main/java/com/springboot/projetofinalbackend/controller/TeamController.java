package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Team;
import com.springboot.projetofinalbackend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {
        return teamService.findAll();
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long teamId) {
        return teamService.findById(teamId);
    }

    @GetMapping("/{teamId}/players")
    public ResponseEntity<Set<Player>> getAllPlayers(@PathVariable Long teamId) {
        return teamService.getAllPlayersTeam(teamId);
    }
}
