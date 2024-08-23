package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.RequestConfirmDTO;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private AuthService authService;

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

    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id, @RequestBody RequestConfirmDTO body) {
        var userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userUpdate.setUsername(body.username());
        UserDTO user = new UserDTO(userUpdate.getId(),
                userUpdate.getUsername(),
                userUpdate.getEmail(),
                userUpdate.getPhotoName(),
                userUpdate.getRole(),
                userUpdate.getPlayer().getId(),
                userUpdate.getCoach().getId(),
                userUpdate.getCredential().getId());

        if(authService.authenticate(body.password(),userUpdate.getPassword())) {
            userRepository.save(userUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId, @RequestBody RequestConfirmDTO body) {
        var userDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(authService.authenticate(body.password(),userDelete.getPassword())){
            userRepository.delete(userDelete);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

