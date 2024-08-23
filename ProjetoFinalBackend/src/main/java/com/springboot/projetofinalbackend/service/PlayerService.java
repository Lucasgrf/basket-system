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

    public ResponseEntity<TeamDTO> team(@RequestBody PlayerDTO playerDTO){
        Player player = playerRepository.findById(playerDTO.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<Team> team = teamRepository.findById(player.getTeam().getId());
        if(team.isPresent()){
            TeamDTO teamDTO = new TeamDTO(team.get().getId(), team.get().getName(), team.get().getAddress(),
                    team.get().getGym(), team.get().getFoundation(), team.get().getEmailContact(), team.get().getPhoneContact(), team.get().getCoach().getId());
            return ResponseEntity.status(HttpStatus.OK).body(teamDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<CredentialDTO> credential(@RequestBody PlayerDTO playerDTO){
        Player player = playerRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<Credential> credential = credentialRepository.findById(player.getId());
        if(credential.isPresent()){
            CredentialDTO credentialDTO = new CredentialDTO(
                    credential.get().getId(),credential.get().getPhotoName(),credential.get().getName(),
                    credential.get().getTeamId(),credential.get().getUserType(),credential.get().getUser().getId()
            );
            return ResponseEntity.status(HttpStatus.OK).body(credentialDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<TrainingDTO>> trainings(@RequestBody PlayerDTO playerDTO){
        Player player = playerRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(player.getTeam() != null){
            List<Training> trainings = trainingRepository.findAll();
            List<TrainingDTO> trainingDTOs = getTrainingDTOS(trainings, player);
            return ResponseEntity.status(HttpStatus.OK).body(trainingDTOs);   
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
