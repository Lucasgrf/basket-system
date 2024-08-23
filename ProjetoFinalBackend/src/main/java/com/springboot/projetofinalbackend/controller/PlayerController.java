package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.service.AdminService;
import com.springboot.projetofinalbackend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/player")
@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @PostMapping("/join")
    public ResponseEntity joinTeam(@RequestBody PlayerDTO playerDTO) {
        return playerService.joinTeam(playerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @DeleteMapping("/leave")
    public ResponseEntity leaveTeam(@RequestBody PlayerDTO playerDTO){
        return playerService.leaveTeam(playerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @PostMapping("/confirm/{trainingId}")
    public ResponseEntity confirmPresenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.confirmPresence(playerDTO, trainingId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @PostMapping("/absence/{trainingId}")
    public ResponseEntity absenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.absenceTraining(playerDTO,trainingId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @GetMapping("/team")
    public ResponseEntity<TeamDTO> viewTeam(@RequestBody PlayerDTO playerDTO){
        return playerService.team(playerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @GetMapping("/credential")
    public ResponseEntity<CredentialDTO> viewCredential(@RequestBody PlayerDTO playerDTO){
        return playerService.credential(playerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingDTO>> viewTrainings(@RequestBody PlayerDTO playerDTO){
        return playerService.trainings(playerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        return adminService.getAllPlayers();
    }

    @PreAuthorize("hasAnyRole('ADMIN','PLAYER', 'COACH')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PlayerDTO> update(@RequestBody PlayerDTO playerDTO, @PathVariable Long id){
        return playerService.updatePlayer(playerDTO,id);
    }
}
