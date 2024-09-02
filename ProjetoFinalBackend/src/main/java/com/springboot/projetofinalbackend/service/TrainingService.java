package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.repository.PlayerRepository;
import com.springboot.projetofinalbackend.repository.TeamRepository;
import com.springboot.projetofinalbackend.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<TrainingDTO> create(@RequestBody TrainingDTO trainingDto){
        var existsTraining = trainingRepository.findByTitle(trainingDto.title());
        if(existsTraining.isEmpty()){
            Training training = new Training();
            training.setDate(trainingDto.date());
            training.setTitle(trainingDto.title());
            training.setLocation(trainingDto.location());
            var existsTeam = teamRepository.findById(trainingDto.teamId());
            if(existsTeam.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            training.setTeam(existsTeam.get());
            trainingRepository.save(training);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(training));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public ResponseEntity<TrainingDTO> update(@PathVariable Long id ,@RequestBody TrainingDTO training){
        var trainingUpdate = trainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        trainingUpdate.setTitle(training.title());
        trainingUpdate.setLocation(training.location());
        trainingUpdate.setDate(training.date());
        trainingRepository.save(trainingUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(trainingUpdate));
    }

    public ResponseEntity<Void> delete(@PathVariable Long id){
        var trainingDelete = trainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(trainingDelete != null){
            trainingRepository.delete(trainingDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public Set<PlayerDTO> addPlayer(Long trainingId, Long playerId) {
        var training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        if (!training.getPlayers().contains(player)) {
            training.getPlayers().add(player);
            trainingRepository.save(training);
        }
        Set<Player> players = training.getPlayers();
        Set<PlayerDTO> playerDTOSet = new HashSet<>();
        for (var player1 : players) {
            PlayerDTO playerDTO = toDTO(player1);
            playerDTOSet.add(playerDTO);
        }
        return playerDTOSet;
    }


    public ResponseEntity<Void> deletePlayer(Long trainingId, Long playerId) {
        var training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        if (training.getPlayers().contains(player)) {
            training.getPlayers().remove(player);
            trainingRepository.save(training);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long trainingId) {
        var training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        TrainingDTO trainingDTO = new TrainingDTO(
                training.getId(),
                training.getTitle(),
                training.getDate(),
                training.getLocation(),
                training.getTeam().getId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(trainingDTO);
    }

    public PlayerDTO toDTO(Player player) {
        return new PlayerDTO(
                player.getId(),
                player.getNickname(),
                player.getUser() != null ? player.getUser().getId() : null,
                player.getPosition(),
                player.getHeight(),
                player.getWeight(),
                player.getAge(),
                player.getTeam() != null ? player.getTeam().getId() : null
        );
    }

    public TrainingDTO toDTO(Training training) {
        return new TrainingDTO(
                training.getId(),
                training.getTitle(),
                training.getDate(),
                training.getLocation(),
                training.getTeam() != null ? training.getTeam().getId() : null
        );
    }

    public ResponseEntity<List<TrainingDTO>> getAllTrainingsByTeam(@PathVariable Long teamId) {
        var trainings = trainingRepository.getTrainingsByTeamId(teamId);
        List<TrainingDTO> trainingDTOList = new ArrayList<>();
        for (var training : trainings) {
            trainingDTOList.add(toDTO(training));
        }
        return ResponseEntity.status(HttpStatus.OK).body(trainingDTOList);
    }
}
