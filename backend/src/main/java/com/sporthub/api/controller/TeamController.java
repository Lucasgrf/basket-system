package com.sporthub.api.controller;

import com.sporthub.api.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO (Phase 3): Full CRUD via TeamCreateRequest/TeamResponse DTOs.
 */
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
}
