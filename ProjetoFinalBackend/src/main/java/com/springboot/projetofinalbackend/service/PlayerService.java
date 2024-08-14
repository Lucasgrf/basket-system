package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;




}
