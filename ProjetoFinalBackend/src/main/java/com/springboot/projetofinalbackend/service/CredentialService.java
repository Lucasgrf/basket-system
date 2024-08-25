package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;

    public Credential create(User user) {
        var credential = new Credential();
        credential.setUser(user);
        credential.setPhotoName("");
        credential.setName(user.getUsername());
        credential.setTeamId(null);
        credential.setUserType(user.getRole().name());
        credentialRepository.save(credential);
        return credential;
    }

    public ResponseEntity<CredentialDTO> update(@PathVariable Long id, @RequestBody CredentialDTO credential) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(credential.name() != null){
            cred.setName(credential.name());
        }
        if(credential.teamId() != null){
            cred.setTeamId(credential.teamId());
        }
        if(credential.userType() != null){
            cred.setUserType(credential.userType());
        }
        if(credential.photoName() != null){
            cred.setPhotoName(credential.photoName());
        }
        credentialRepository.save(cred);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(cred));
    }

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        credentialRepository.delete(cred);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<CredentialDTO> get(@PathVariable Long id) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(cred));
    }

    public CredentialDTO toDTO(Credential credential) {
        return new CredentialDTO(
                credential.getId(),
                credential.getPhotoName() != null ? credential.getPhotoName() : null,
                credential.getName(),
                credential.getTeamId() != null ? credential.getTeamId() : null,
                credential.getUserType(),
                credential.getUser().getId()
        );
    }
}

