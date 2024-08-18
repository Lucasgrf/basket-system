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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<List<Team>> findAll() {
        List<Team> teams = teamRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    public ResponseEntity<Team> findById(@PathVariable Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


    public ResponseEntity<Team> create(@RequestBody TeamDTO teamDto) {
        var team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamRepository.save(team));
    }

    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody TeamDTO team) {
        var teamUpdate = teamRepository.findById(id).orElse(null);
        if (teamUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        teamUpdate.setName(team.name());
        teamUpdate.setAddress(team.address());
        teamUpdate.setGym(team.gym());
        teamUpdate.setEmailContact(team.emailContact());
        teamUpdate.setFoundation(team.foundation());
        teamUpdate.setPhoneContact(team.phoneContact());

        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.save(teamUpdate));
    }


    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String confirmation) {
        var teamDelete = teamRepository.findById(id).orElse(null);
        if (teamDelete == null || !confirmation.equals(teamDelete.getName())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        teamRepository.delete(teamDelete);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Set<Player>> getAllPlayersTeam(@PathVariable Long teamId) {
        var team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(team.getPlayers());
    }
}
