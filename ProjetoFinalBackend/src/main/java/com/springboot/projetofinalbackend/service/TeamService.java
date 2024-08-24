package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.PlayerDTO;
import com.springboot.projetofinalbackend.DTO.TeamDTO;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.Team;
import com.springboot.projetofinalbackend.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO teamDto) {
        var existsTeam = teamRepository.findByName(teamDto.name());
        if (existsTeam.isEmpty()) {
            Team team = new Team();
            BeanUtils.copyProperties(teamDto, team);
            teamRepository.save(team);
            BeanUtils.copyProperties(team, teamDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(teamDto);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(teamDto);
    }

    public ResponseEntity<TeamDTO> update(@PathVariable Long id, @RequestBody TeamDTO team) {
        var teamUpdate = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        teamUpdate.setName(team.name());
        teamUpdate.setAddress(team.address());
        teamUpdate.setGym(team.gym());
        teamUpdate.setEmailContact(team.emailContact());
        teamUpdate.setFoundation(team.foundation());
        teamUpdate.setPhoneContact(team.phoneContact());
        teamRepository.save(teamUpdate);
        TeamDTO teamDTO = new TeamDTO(
                teamUpdate.getId(),
                teamUpdate.getName(),
                teamUpdate.getAddress(),
                teamUpdate.getGym(),
                teamUpdate.getFoundation(),
                teamUpdate.getPhoneContact(),
                teamUpdate.getPhoneContact(),
                teamUpdate.getCoach().getId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(teamDTO);
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
        TeamDTO teamDTO = new TeamDTO(
            team.getId(),
            team.getName(),
            team.getAddress(),
            team.getEmailContact(),
            team.getFoundation(),
            team.getPhoneContact(),
            team.getGym(),
            team.getCoach().getId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(teamDTO);
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
}
