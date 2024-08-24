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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;

    public Credential create(User user) {
        var credential = new Credential();
        credential.setUser(user);
        credential.setPhotoName(null);
        credential.setName(user.getUsername());
        credential.setTeamId(null);
        credential.setUserType(user.getRole().name());
        credentialRepository.save(credential);
        return credential;
    }

    public ResponseEntity<CredentialDTO> update(@PathVariable Long id, @RequestBody CredentialDTO credential) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cred.setPhotoName(credential.photoName());
        cred.setName(credential.name());
        cred.setTeamId(credential.teamId());
        credentialRepository.save(cred);
        CredentialDTO credentialDTO = new CredentialDTO(
          cred.getId(),cred.getPhotoName(),cred.getName(),cred.getTeamId(),cred.getUserType(),cred.getUser().getId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(credentialDTO);
    }

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(cred != null){
            credentialRepository.delete(cred);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

