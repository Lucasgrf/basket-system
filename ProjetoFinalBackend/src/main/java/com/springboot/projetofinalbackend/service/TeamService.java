package com.springboot.projetofinalbackend.service;

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

import java.util.Set;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    public ResponseEntity<Team> create(@RequestBody TeamDTO teamDto) {
        var team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamRepository.save(team));
    }

    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody TeamDTO team) {
        var teamUpdate = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        teamUpdate.setName(team.name());
        teamUpdate.setAddress(team.address());
        teamUpdate.setGym(team.gym());
        teamUpdate.setEmailContact(team.emailContact());
        teamUpdate.setFoundation(team.foundation());
        teamUpdate.setPhoneContact(team.phoneContact());

        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.save(teamUpdate));
    }


    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String confirmation) {
        var teamDelete = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(confirmation.equals(teamDelete.getName())) {
            teamRepository.delete(teamDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<Set<Player>> getAllPlayersTeam(@PathVariable Long teamId) {
        var team = teamRepository.findById(teamId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(team.getPlayers());
    }
}
