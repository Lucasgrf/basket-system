package com.sporthub.api.controller;

import com.sporthub.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * TODO (Phase 3): Rebuild with proper endpoints for admin operations.
 * Admin-specific operations will use @PreAuthorize("hasRole('ADMIN')") on domain service methods.
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
}
