package com.springboot.projetofinalbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String photoName;

    @Column(nullable = false, unique = true)
    private String name;

    private Long TeamId;

    private String role;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

