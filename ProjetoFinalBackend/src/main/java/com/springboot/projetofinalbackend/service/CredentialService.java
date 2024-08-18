package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    public ResponseEntity<Credential> update(@PathVariable Long id, @RequestBody CredentialDTO credential) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credential not found"));

        cred.setPhotoName(credential.photoName());
        cred.setName(credential.name());
        cred.setTeamId(credential.teamId());
        credentialRepository.save(cred);

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String confirmation) {
        var cred = credentialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credential not found"));
        if(confirmation.equals(cred.getName())){
            credentialRepository.delete(cred);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

