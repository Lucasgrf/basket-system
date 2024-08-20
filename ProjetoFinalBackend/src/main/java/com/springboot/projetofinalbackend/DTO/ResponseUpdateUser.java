package com.springboot.projetofinalbackend.DTO;

import com.springboot.projetofinalbackend.model.User;

public record ResponseUpdateUser(String username, String password, User.Role role, String photoName, String email) {
}
