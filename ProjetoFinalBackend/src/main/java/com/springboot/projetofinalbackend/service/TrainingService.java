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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public ResponseEntity<Training> create(@RequestBody TrainingDTO trainingDto){
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

    public ResponseEntity<Training> update(@PathVariable Long id ,@RequestBody TrainingDTO training){
        var trainingUpdate = trainingRepository.findById(id).orElse(null);
        if(trainingUpdate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        trainingUpdate.setTitle(training.title());
        trainingUpdate.setLocation(training.location());
        trainingUpdate.setDateTime(training.dateTime());
        return ResponseEntity.status(HttpStatus.OK).body(trainingRepository.save(trainingUpdate));
    }

    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String confirmation){
        var trainingDelete = trainingRepository.findById(id).orElse(null);
        if(trainingDelete == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!trainingDelete.getTitle().equals(confirmation)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        trainingRepository.delete(trainingDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public Set<Player> addPlayer(Long trainingId, Long playerId) {
        var training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

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
