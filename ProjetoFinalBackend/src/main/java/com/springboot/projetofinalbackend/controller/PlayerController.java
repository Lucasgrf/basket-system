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
//@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private AdminService adminService;

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @PostMapping("/join")
    public ResponseEntity joinTeam(@RequestBody PlayerDTO playerDTO) {
        return playerService.joinTeam(playerDTO);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @DeleteMapping("/leave")
    public ResponseEntity leaveTeam(@RequestBody PlayerDTO playerDTO){
        return playerService.leaveTeam(playerDTO);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @PostMapping("/confirm/{trainingId}")
    public ResponseEntity confirmPresenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.confirmPresence(playerDTO, trainingId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @PostMapping("/absence/{trainingId}")
    public ResponseEntity absenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.absenceTraining(playerDTO,trainingId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @GetMapping("/team/{playerId}")
    public ResponseEntity<TeamDTO> viewTeam(@PathVariable Long playerId){
        return playerService.team(playerId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @GetMapping("/credential/{playerId}")
    public ResponseEntity<CredentialDTO> viewCredential(@PathVariable Long playerId){
        return playerService.credential(playerId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER')")
    @GetMapping("/trainings/{playerId}")
    public ResponseEntity<List<TrainingDTO>> viewTrainings(@PathVariable Long playerId){
        return playerService.trainings(playerId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        return adminService.getAllPlayers();
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PLAYER', 'COACH')")
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable Long id,@RequestBody PlayerDTO playerDTO){
        return playerService.updatePlayer(playerDTO,id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getById(@PathVariable Long id){
        return playerService.getPlayer(id);
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO){
        return playerService.create(playerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return playerService.delete(id);
    }
}
