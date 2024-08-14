package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Training;
import com.springboot.projetofinalbackend.repository.PlayerRepository;
import com.springboot.projetofinalbackend.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public ResponseEntity<Training> save(TrainingDTO trainingDto){
        var training = new Training();
        BeanUtils.copyProperties(trainingDto, training);
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingRepository.save(training));
    }

    public ResponseEntity<List<Training>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(trainingRepository.findAll());
    }

    public ResponseEntity<Training> findById(Long id){
        return ResponseEntity.status(HttpStatus.OK).body(trainingRepository.findById(id).orElse(null));
    }

    public ResponseEntity<Training> update(Training training){
        var trainingUpdate = trainingRepository.findById(training.getId()).orElse(null);
        if(trainingUpdate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        training.setTitle(training.getTitle());
        training.setLocation(training.getLocation());
        training.setDateTime(training.getDateTime());
        return ResponseEntity.status(HttpStatus.OK).body(trainingRepository.save(training));
    }

    public ResponseEntity<Void> delete(Training training){
        var trainingDelete = trainingRepository.findById(training.getId()).orElse(null);
        if(trainingDelete == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        trainingRepository.delete(trainingDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public Set<Player> addPlayer(Long trainingId, Long playerId) {
        var training = trainingRepository.findById(trainingId).orElseThrow(() -> new EntityNotFoundException("Training not found"));
        var player = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player not found"));

        if (!training.getPlayers().contains(player)) {
            training.getPlayers().add(player);
            trainingRepository.save(training);
        }

        return training.getPlayers();
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

}
