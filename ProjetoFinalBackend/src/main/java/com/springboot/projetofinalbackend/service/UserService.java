package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody UserDTO user) {
        var userUpdate = userRepository.findById(id).orElse(null);
        if (userUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userUpdate.setUsername(user.username());
        userUpdate.setPhotoName(user.photoName());

        if(authService.authenticate(user.password(),userUpdate.getPassword())) {
            userRepository.save(userUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    public ResponseEntity<Void> deleteProfile(@PathVariable Long id, @RequestBody @Valid UserDTO user) {
        var userDelete = userRepository.findById(id).orElse(null);
        if (userDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(user.email().equals(userDelete.getEmail()) && user.username().equals(userDelete.getUsername())) {
            if(authService.authenticate(user.password(),userDelete.getPassword())){
                userRepository.delete(userDelete);
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

