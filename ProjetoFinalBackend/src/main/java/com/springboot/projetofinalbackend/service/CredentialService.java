package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    //Revisar
    public ResponseEntity update(CredentialDTO credential) {
        var cred = credentialRepository.findById(credential.id()).orElseThrow(() -> new RuntimeException("Credential not found"));
        if(cred != null) {
            cred.setPhotoName(credential.photoName());
            cred.setName(credential.name());
            cred.setTeamId(credential.teamId());
            credentialRepository.save(cred);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

