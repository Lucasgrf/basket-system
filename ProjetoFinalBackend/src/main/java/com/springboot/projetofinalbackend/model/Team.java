package com.springboot.projetofinalbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private String gym;

    @Column(nullable = false, unique = true)
    private Date foundation;

    @Column(nullable = false, unique = true)
    private String emailContact;

    @Column(nullable = false, unique = true)
    private String phoneContact;

    @OneToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private Set<Training> trainings;

    @OneToMany(mappedBy = "team")
    private Set<Player> players;

}

