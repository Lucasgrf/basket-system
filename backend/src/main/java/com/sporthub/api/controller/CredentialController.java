package com.sporthub.api.controller;

import com.sporthub.api.DTO.CredentialDTO;
import com.sporthub.api.DTO.UserDTO;
import com.sporthub.api.model.Credential;
import com.sporthub.api.service.AdminService;
import com.sporthub.api.service.CredentialService;
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
