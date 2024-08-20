package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/trainings")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COACH')")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

}
