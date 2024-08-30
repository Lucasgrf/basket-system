package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.model.*;
import com.springboot.projetofinalbackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CredentialService credentialService;

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
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(team));
    }

    public ResponseEntity<CredentialDTO> credential(@PathVariable Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));

        Credential credential = credentialRepository.findByUserId(player.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credential not found"));

        return ResponseEntity.status(HttpStatus.OK).body(toDTO(credential));
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
        var existsUser = userRepository.findById(playerDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        player.setUser(existsUser);
        if(playerDTO.teamId() != null){
            teamRepository.findById(playerDTO.teamId()).ifPresent(player::setTeam);
        }
        if(playerDTO.age() != 0){
            player.setAge(playerDTO.age());
        }
        if(playerDTO.height() != 0){
            player.setHeight(playerDTO.height());
        }
        if(playerDTO.weight() != 0){
            player.setWeight(playerDTO.weight());
        }
        if(playerDTO.position() != null){
            player.setPosition(playerDTO.position());
        }
        playerRepository.save(player);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(player));
    }

    public CredentialDTO toDTO(Credential credential) {
        return new CredentialDTO(
                credential.getId(),
                credential.getPhotoName(),
                credential.getName(),
                credential.getTeamId(),
                credential.getUserType(),
                credential.getUser() != null ? credential.getUser().getId() : null
        );
    }

    public TeamDTO toDTO(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getAddress(),
                team.getGym(),
                team.getFoundation(),
                team.getEmailContact(),
                team.getPhoneContact(),
                team.getCoach() != null ? team.getCoach().getId() : null
        );
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

    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long id) {
        var player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(player));
    }

    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO) {
        var existsPlayer = playerRepository.findByNickname(playerDTO.nickname());
        if(existsPlayer.isEmpty()){
            Player player = new Player();
            player.setNickname(playerDTO.nickname());
            player.setAge(playerDTO.age());
            player.setPosition(playerDTO.position());
            player.setHeight(playerDTO.height());
            player.setWeight(playerDTO.weight());
            if(playerDTO.teamId() != null){
                teamRepository.findById(playerDTO.teamId()).ifPresent(player::setTeam);
            }
            if (playerDTO.userId() != null) {
                User user = userRepository.findById(playerDTO.userId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                player.setUser(user);
                playerRepository.save(player);
                return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(player));
            }
            User user = generateRandomUser();
            player.setUser(user);
            playerRepository.save(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(player));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity delete(Long id) {
        var player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        playerRepository.delete(player);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public User generateRandomUser(){
        User user = new User();

        List<String> nomes = Arrays.asList("João", "Pedro", "Carlos", "Lucas", "Miguel", "Gabriel", "Rafael", "Felipe", "Gustavo");


        Random random = new Random();
        int randomNumber = random.nextInt(100); // Gera um número entre 0 e 99
        String randomName = nomes.get(random.nextInt(nomes.size())) + random.nextInt(100); // Seleciona um nome aleatório e adiciona um número aleatório

        String email = randomName + randomNumber + "@gmail.com";
        user.setEmail(email);

        user.setUsername(randomName);
        user.setPhotoName("");
        user.setPassword(passwordEncoder.encode("padrao@user" + randomName));
        user.setRole(User.Role.PLAYER);
        userRepository.save(user);
        Credential credential = credentialService.create(user);
        user.setCredential(credential);
        userRepository.save(user);
        return user;
    }

    public ResponseEntity<PlayerDTO> getPlayerByUserId(@PathVariable Long userId){
        var player = playerRepository.findByUserId(userId);
        if(player.isPresent()){
            Player playerAux = player.get();
            return ResponseEntity.status(HttpStatus.OK).body(toDTO(playerAux));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
