package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
