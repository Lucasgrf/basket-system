package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.model.*;
import com.springboot.projetofinalbackend.repository.CoachRepository;
import com.springboot.projetofinalbackend.repository.TeamRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO teamDto) {
        var existsTeam = teamRepository.findByName(teamDto.name());
        var existsCoach = coachRepository.findById(teamDto.coachId());
        if (existsTeam.isEmpty()) {
            Team team = new Team();
            team.setName(teamDto.name());
            team.setAddress(teamDto.address());
            team.setGym(teamDto.gym());
            team.setFoundation(teamDto.foundation());
            team.setEmailContact(teamDto.emailContact());
            team.setPhoneContact(teamDto.phoneContact());
            if (existsCoach.isEmpty()) {
                Coach coach = new Coach();
                User user = new User();

                // Lista de nomes
                List<String> nomes = Arrays.asList("João", "Maria", "Pedro", "Ana", "Carlos", "Beatriz", "Lucas", "Fernanda");

                // Gerar número aleatório
                Random random = new Random();
                int randomNumber = random.nextInt(100); // Gera um número entre 0 e 99
                String randomName = nomes.get(random.nextInt(nomes.size())) + random.nextInt(100); // Seleciona um nome aleatório e adiciona um número aleatório

                // Adicionar número aleatório ao e-mail
                String email = randomName + randomNumber + "@gmail.com";
                user.setEmail(email);

                // Definir nome aleatório com número
                user.setUsername(randomName);
                user.setPassword("12345678");
                user.setPhotoName("");
                user.setPassword(passwordEncoder.encode("padrao@user"));
                user.setRole(User.Role.COACH);
                userRepository.save(user);
                Credential credential = credentialService.create(user);
                user.setCredential(credential);
                userRepository.save(user);
                coach.setUser(user);
                coachRepository.save(coach);
                team.setCoach(coach);
                teamRepository.save(team);
                return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(team));
            }
            team.setCoach(existsCoach.get());
            teamRepository.save(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(team));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }



    public ResponseEntity<TeamDTO> update(@PathVariable Long id, @RequestBody TeamDTO teamDto) {
        var teamUpdate = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        teamUpdate.setName(teamDto.name());
        teamUpdate.setAddress(teamDto.address());
        teamUpdate.setGym(teamDto.gym());
        teamUpdate.setEmailContact(teamDto.emailContact());
        teamUpdate.setFoundation(teamDto.foundation());
        teamUpdate.setPhoneContact(teamDto.phoneContact());
        var existsCoach = coachRepository.findById(teamDto.coachId());
        if (existsCoach.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        teamUpdate.setCoach(existsCoach.get());
        teamRepository.save(teamUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(teamUpdate));
    }



    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var teamDelete = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(teamDelete != null) {
            teamRepository.delete(teamDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<Set<PlayerDTO>> getAllPlayersTeam(@PathVariable Long teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Set<Player> players = team.getPlayers();
        Set<PlayerDTO> playerDTOSet = new HashSet<>();
        for (var player : players) {
            PlayerDTO playerDTO = toDTO(player);
            playerDTOSet.add(playerDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerDTOSet);
    }

    public ResponseEntity<TeamDTO> getTeam(@PathVariable Long teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(team));
    }

    public PlayerDTO toDTO(Player player) {
        return new PlayerDTO(
                player.getId(),
                player.getUser() != null ? player.getUser().getId() : null,
                player.getPosition(),
                player.getHeight(),
                player.getWeight(),
                player.getAge(),
                player.getTeam() != null ? player.getTeam().getId() : null
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
}
