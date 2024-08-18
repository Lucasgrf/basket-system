package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
