package com.sporthub.api.controller;

import com.sporthub.api.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO (Phase 3): Replace with TrainingSessionController.
 */
@RestController
@RequestMapping("/training-sessions")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
}
