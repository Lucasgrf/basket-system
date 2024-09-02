package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.RequestUpdateUser;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CredentialRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.springboot.projetofinalbackend.service.AdminService.getUserDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private CredentialRepository credentialRepository;

    public ResponseEntity<UserDTO> getById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(toDTO(user.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id, @RequestBody RequestUpdateUser user) {
        var userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        updateUserFields(userUpdate, user);

        if (userUpdate.getCredential() == null) {
            credentialRepository.save(credentialService.create(userUpdate));
        }

        userRepository.save(userUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(toDTO(userUpdate));
    }

    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        var userDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(userDelete != null){
            userRepository.delete(userDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public UserDTO toDTO(User user) {
        return getUserDTO(user);
    }

    private void updateUserFields(User userUpdate, RequestUpdateUser user) {
        if (user.username() != null && !user.username().trim().isEmpty()) {
            userUpdate.setUsername(user.username());
        }
        if (user.email() != null && !user.email().trim().isEmpty()) {
            userUpdate.setEmail(user.email());
        }
        if (user.photoName() != null && !user.photoName().trim().isEmpty()) {
            userUpdate.setPhotoName(user.photoName());
        }
        if (user.password() != null && !user.password().trim().isEmpty()) {
            userUpdate.setPassword(passwordEncoder.encode(user.password()));
        }
    }

}

