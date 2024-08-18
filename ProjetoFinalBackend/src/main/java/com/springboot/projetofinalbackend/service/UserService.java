package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody UserDTO user) {
        var userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userUpdate.setUsername(user.username());
        userUpdate.setPhotoName(user.photoName());

        if(authService.authenticate(user.password(),userUpdate.getPassword())) {
            userRepository.save(userUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    public ResponseEntity<Void> deleteProfile(@PathVariable Long id, @RequestParam String password) {
        var userDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(authService.authenticate(password,userDelete.getPassword())){
            userRepository.delete(userDelete);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

