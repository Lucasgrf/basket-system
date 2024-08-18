package com.springboot.projetofinalbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    private String position;

    private double height;

    private double weight;

    private int age;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany(mappedBy = "players")
    private Set<Training> trainings = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "player_confirmed_trainings",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "training_id")
    )
    private Set<Training> confirmedTrainings = new HashSet<>();


}

