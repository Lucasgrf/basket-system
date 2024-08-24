package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Team;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.repository.CredentialRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private CredentialRepository credentialRepository;

    public ResponseEntity joinTeam(@RequestBody PlayerDTO playerDTO) {
        var player = playerRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        var team = teamRepository.findById(playerDTO.teamId()).orElse(null);

        if (player.getTeam() != null || team == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(player);
        }

        player.setTeam(team);

        playerRepository.save(player);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity leaveTeam(@RequestBody PlayerDTO playerDTO) {
        var player = playerRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        if (player.getTeam() == null) {
            throw new IllegalStateException("The player is not linked to a team.");
        }

        player.setTeam(null);
        playerRepository.save(player);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity confirmPresence(@RequestBody PlayerDTO playerDTO, @PathVariable Long trainingId) {
        Player player = playerRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found."));

        if (!training.getPlayers().contains(player)) {
            throw new IllegalStateException("Player is not registered for this training.");
        }

        training.getConfirmedPlayers().add(player);

        trainingRepository.save(training);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public ResponseEntity absenceTraining(@RequestBody PlayerDTO playerDTO, @PathVariable Long id) {
        Player player = playerRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found."));

        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found."));

        if (!training.getConfirmedPlayers().contains(player)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(training);
        }

        training.getConfirmedPlayers().remove(player);

        trainingRepository.save(training);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<TeamDTO> team(@PathVariable Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));

        Team team = player.getTeam();
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Retorna 404 se o jogador não tiver um time
        }

        TeamDTO teamDTO = new TeamDTO(
                team.getId(),
                team.getName(),
                team.getAddress(),
                team.getGym(),
                team.getFoundation(),
                team.getEmailContact(),
                team.getPhoneContact(),
                team.getCoach().getId()
        );

        return ResponseEntity.status(HttpStatus.OK).body(teamDTO);
    }

    public ResponseEntity<CredentialDTO> credential(@PathVariable Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));

        Credential credential = credentialRepository.findByPlayerId(player.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credential not found"));

        CredentialDTO credentialDTO = new CredentialDTO(
                credential.getId(),
                credential.getPhotoName(),
                credential.getName(),
                credential.getTeamId(),
                credential.getUserType(),
                credential.getUser().getId()
        );

        return ResponseEntity.status(HttpStatus.OK).body(credentialDTO);
    }

    public ResponseEntity<List<TrainingDTO>> trainings(@PathVariable Long playerId){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Training> trainings = trainingRepository.findByPlayersId(player.getId());
        List<TrainingDTO> trainingDTOs = getTrainingDTOS(trainings, player);

        if (trainingDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(trainingDTOs);
        }
    }

    private List<TrainingDTO> getTrainingDTOS(List<Training> trainings, Player player) {
        List<TrainingDTO> trainingDTOs = new ArrayList<>();
        for (Training training : trainings) {
            if(training.getTeam().getId().equals(player.getTeam().getId())){
                TrainingDTO trainingDTO = new TrainingDTO(
                        training.getId(),training.getTitle(),training.getDate(),
                        training.getLocation(), training.getTeam().getId()
                );
                trainingDTOs.add(trainingDTO);
            }
        }
        return trainingDTOs;
    }

    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO playerDTO, @PathVariable Long id){
        var player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(player.getTeam() != null){
            BeanUtils.copyProperties(playerDTO, player);
            playerRepository.save(player);
            BeanUtils.copyProperties(player, playerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
