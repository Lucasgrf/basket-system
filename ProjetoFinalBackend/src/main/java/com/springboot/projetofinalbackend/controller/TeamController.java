package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.service.AdminService;
import com.springboot.projetofinalbackend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/team")
@PreAuthorize("hasAnyRole('ADMIN','COACH')")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @GetMapping("/{teamId}/players")
    public ResponseEntity<Set<PlayerDTO>> getAllPlayers(@PathVariable Long teamId) {
        return teamService.getAllPlayersTeam(teamId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COACH')")
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        return teamService.getTeam(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeam(){
        return adminService.getAllTeams();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        return adminService.deleteTeam(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<TeamDTO> addTeam(TeamDTO body){
        return adminService.createTeam(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamDTO team){
        return adminService.updateTeam(id, team);
    }
}
