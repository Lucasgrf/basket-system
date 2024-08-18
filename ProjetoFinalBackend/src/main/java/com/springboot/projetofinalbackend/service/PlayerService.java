package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.repository.PlayerRepository;
import com.springboot.projetofinalbackend.repository.TeamRepository;
import com.springboot.projetofinalbackend.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    public ResponseEntity<Player> create(@RequestBody PlayerDTO playerDTO) {
        var player = new Player();
        BeanUtils.copyProperties(playerDTO, player);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerRepository.save(player));
    }

    public ResponseEntity<Player> joinTeam(@RequestBody PlayerDTO playerDTO) {
        var player = playerRepository.findById(playerDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        var team = teamRepository.findById(playerDTO.teamId()).orElse(null);

        if (player.getTeam() != null || team == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(player);
        }

        player.setTeam(team);

        playerRepository.save(player);

        return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    public ResponseEntity<Void> leaveTeam(@RequestBody PlayerDTO playerDTO) {
        var player = playerRepository.findById(playerDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        if (player.getTeam() == null) {
            throw new IllegalStateException("The player is not linked to a team.");
        }

        player.setTeam(null);
        playerRepository.save(player);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Training> confirmPresence(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId) {
        Player player = playerRepository.findById(playerDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found."));

        if (!training.getPlayers().contains(player)) {
            throw new IllegalStateException("Player is not registered for this training.");
        }

        training.getConfirmedPlayers().add(player);

        trainingRepository.save(training);

        return ResponseEntity.status(HttpStatus.OK).body(training);
    }


    public ResponseEntity<Training> absenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long id) {
        Player player = playerRepository.findById(playerDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found."));

        if (!training.getConfirmedPlayers().contains(player)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(training);
        }

        training.getConfirmedPlayers().remove(player);

        trainingRepository.save(training);

        return ResponseEntity.status(HttpStatus.OK).body(training);
    }
}
