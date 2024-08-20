package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/player")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PLAYER')")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/join")
    public ResponseEntity joinTeam(@RequestBody PlayerDTO playerDTO) {
        return playerService.joinTeam(playerDTO);
    }

    @DeleteMapping("/leave")
    public ResponseEntity leaveTeam(@RequestBody PlayerDTO playerDTO){
        return playerService.leaveTeam(playerDTO);
    }

    @PostMapping("/confirm/{trainingId}")
    public ResponseEntity confirmPresenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.confirmPresence(playerDTO, trainingId);
    }

    @PostMapping("/absence/{trainingId}")
    public ResponseEntity absenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.absenceTraining(playerDTO,trainingId);
    }

    @GetMapping("/team")
    public ResponseEntity viewTeam(@RequestBody PlayerDTO playerDTO){
        return playerService.team(playerDTO);
    }

    @GetMapping("/credential")
    public ResponseEntity viewCredential(@RequestBody PlayerDTO playerDTO){
        return playerService.credential(playerDTO);
    }

    @GetMapping("/trainings")
    public ResponseEntity viewTrainings(@RequestBody PlayerDTO playerDTO){
        return playerService.trainings(playerDTO);
    }
}
