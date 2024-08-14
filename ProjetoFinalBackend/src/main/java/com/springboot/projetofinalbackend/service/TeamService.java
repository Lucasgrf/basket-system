package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.TeamDTO;
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

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<List<Team>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.findAll());
    }

    public ResponseEntity<Team> findById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.findById(id).orElse(null));
    }

    public ResponseEntity<Team> create(@RequestBody TeamDTO teamDto) {
        var team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamRepository.save(team));
    }

    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody Team team) {
        var teamUpdate = teamRepository.findById(id).orElse(null);
        if (teamUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        teamUpdate.setName(team.getName());
        teamUpdate.setAddress(team.getAddress());
        teamUpdate.setGym(team.getGym());
        teamUpdate.setEmailContact(team.getEmailContact());
        teamUpdate.setFoundation(team.getFoundation());
        teamUpdate.setPhoneContact(team.getPhoneContact());

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
}
