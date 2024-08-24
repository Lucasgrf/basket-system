package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.RequestUpdateUser;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<UserDTO> getById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            UserDTO userDTO = new UserDTO(user.get().getId(),
                    user.get().getUsername(),
                    user.get().getEmail(),
                    user.get().getPhotoName(),
                    user.get().getRole(), user.get().getPlayer().getId(),
                    user.get().getCoach().getId(),user.get().getCredential().getId());
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id, @RequestBody RequestUpdateUser user) {
        var userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(userUpdate != null){
            userUpdate.setUsername(user.username());
            userUpdate.setEmail(user.email());
            userUpdate.setPhotoName(user.photoName());
            userUpdate.setPassword(passwordEncoder.encode(user.password()));
            userRepository.save(userUpdate);

            return ResponseEntity.status(HttpStatus.OK).body(toDTO(userUpdate));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhotoName(),
                user.getRole(),
                user.getPlayer() != null ? user.getPlayer().getId() : null,
                user.getCoach() != null ? user.getCoach().getId() : null,
                user.getCredential() != null ? user.getCredential().getId() : null
        );
    }

}

