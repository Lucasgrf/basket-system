package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        var user = new User();
        BeanUtils.copyProperties(userDTO, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!credentialService.create(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    //Incompleto: Spring security e JWTs
    public ResponseEntity<Void> login(UserDTO user) {
        var userAuthenticate = userRepository.findByEmail(user.email());
        if (authenticate(user.password(),userAuthenticate.getPassword())) {
            // gerar e retornar um token de autenticação
            // return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse("token"));
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody UserDTO user) {
        var userUpdate = userRepository.findById(id).orElse(null);
        if (userUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userUpdate.setFullName(user.fullName());
        userUpdate.setUsername(user.username());

        if(authenticate(user.password(),userUpdate.getPassword())) {
            userRepository.save(userUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    public ResponseEntity<Void> deleteProfile(@PathVariable Long id, @RequestBody UserDTO user,@RequestParam String confirmPassword) {
        var userDelete = userRepository.findById(id).orElse(null);
        if (userDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(authenticate(confirmPassword,userDelete.getPassword())){
            userRepository.delete(userDelete);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

