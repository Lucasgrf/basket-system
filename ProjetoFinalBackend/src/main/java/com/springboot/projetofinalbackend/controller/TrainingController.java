package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.TrainingDTO;
import com.springboot.projetofinalbackend.service.AdminService;
import com.springboot.projetofinalbackend.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/training")
//@PreAuthorize("hasAnyRole('ADMIN','COACH', 'PLAYER')")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private AdminService adminService;

    //@PreAuthorize("hasAnyRole('ADMIN','COACH', 'PLAYER')")
    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) {
        return trainingService.getTraining(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings(){
        return adminService.getAllTrainings();
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id){
        return adminService.deleteTraining(id);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<TrainingDTO> addTraining(@RequestBody TrainingDTO body){
        return adminService.createTraining(body);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id, @RequestBody TrainingDTO body){
        return adminService.updateTraining(id,body);
    }

}
