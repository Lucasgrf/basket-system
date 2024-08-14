package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialService credentialService;

    /*@Autowired Tem que colocar esse troço
    private PasswordEncoder passwordEncoder;*/


    // Incompleto: sem JWT e security
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        // Verificar se o usuário já existe
        if (userRepository.findByEmail(userDTO.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        // Converter UserDTO para User
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);

        // Criptografar a senha do usuário
        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Criar credenciais
        if (!credentialService.create(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Salvar o usuário e retornar a resposta
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    //Incompleto: Spring security e JWTs
    public ResponseEntity<Void> login(User user) {
        var userAuthenticate = userRepository.findByEmail(user.getEmail());
        if (userAuthenticate != null && userAuthenticate.getPassword().equals(user.getPassword())) {
            // gerar e retornar um token de autenticação
            // return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse("token"));
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    public ResponseEntity<User> updateProfile(User user) {
        var userUpdate = userRepository.findByEmail(user.getEmail());
        if (userUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userUpdate.setFullName(user.getFullName());
        userUpdate.setUsername(user.getUsername());

        // Atualiza a senha com hashing se for necessário
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            //userUpdate.setPassword(hashPassword(user.getPassword())); // Método de hashing da senha
        }

        userRepository.save(userUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }

    /* Implementar hashing da senha, usando BCrypt
    private String hashPassword(String password) {
        return BCryptPasswordEncoder.encode(password);
    }*/


    public ResponseEntity<Void> deleteProfile(User user, String confirmation) {
        var userDelete = userRepository.findByEmail(user.getEmail());
        if (userDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!confirmation.equals(userDelete.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userRepository.delete(userDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

