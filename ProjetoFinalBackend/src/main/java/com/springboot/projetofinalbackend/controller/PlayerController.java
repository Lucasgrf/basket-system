package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/join")
    public ResponseEntity<Player> joinTeam(@RequestBody PlayerDTO playerDTO) {
        return playerService.joinTeam(playerDTO);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leaveTeam(@RequestBody PlayerDTO playerDTO){
        return playerService.leaveTeam(playerDTO);
    }

    @PostMapping("/confirm/{trainingId}")
    public ResponseEntity<Training> confirmPresenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.confirmPresence(playerDTO, trainingId);
    }

    @PostMapping("/absence/{trainingId}")
    public ResponseEntity<Training> absenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId){
        return playerService.absenceTraining(playerDTO,trainingId);
    }
}
