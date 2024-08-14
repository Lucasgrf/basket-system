package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CredentialRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean create(User user) {
        var credential = new Credential();
        credential.setUser(user);
        credential.setPhotoName(user.getPhotoName());
        credential.setName(user.getFullName());
        credential.setTeamId(null);
        credential.setRole(null);
        credentialRepository.save(credential);
        return credentialRepository.existsById(credential.getId());
    }
}

