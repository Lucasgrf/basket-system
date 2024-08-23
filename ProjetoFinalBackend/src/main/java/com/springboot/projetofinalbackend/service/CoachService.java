package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachService {
    @Autowired
    private CoachRepository coachRepository;

}
