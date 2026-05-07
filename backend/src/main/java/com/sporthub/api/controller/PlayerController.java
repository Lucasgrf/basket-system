package com.sporthub.api.controller;

import com.sporthub.api.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO (Phase 3): Replace with AthleteController.
 */
@RestController
@RequestMapping("/athletes")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
}
