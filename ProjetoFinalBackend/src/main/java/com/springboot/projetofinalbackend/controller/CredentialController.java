package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.CredentialDTO;
import com.springboot.projetofinalbackend.DTO.UserDTO;
import com.springboot.projetofinalbackend.model.Credential;
import com.springboot.projetofinalbackend.service.AdminService;
import com.springboot.projetofinalbackend.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CredentialService credentialService;

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CredentialDTO>> getAllCredential(){
        return adminService.getAllCredentials();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredentialDTO> getCredentialById(@PathVariable Long id){
        return credentialService.get(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredential(@PathVariable Long id){
        return adminService.deleteCredential(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CredentialDTO> updateCredential(@PathVariable Long id, @RequestBody CredentialDTO credential){
        return adminService.updateCredential(id, credential);
    }
}
