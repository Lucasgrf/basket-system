package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;


}
