package com.sporthub.api.controller;

import com.sporthub.api.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO (Phase 3): Replace with TechnicianController.
 */
@RestController
@RequestMapping("/technicians")
@RequiredArgsConstructor
public class CoachController {
    private final CoachService coachService;
}
