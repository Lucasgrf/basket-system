package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/teams")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COACH')")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/{teamId}/players")
    public ResponseEntity<Set<Player>> getAllPlayers(@PathVariable Long teamId) {
        return teamService.getAllPlayersTeam(teamId);
    }
}
