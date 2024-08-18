package com.springboot.projetofinalbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    @JoinTable(
            name = "training_credential",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "credential_id")
    )
    private List<Credential> credentials;

    @ManyToMany
    @JoinTable(
            name = "player_training", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "training_id"), // Chave estrangeira para a tabela Training
            inverseJoinColumns = @JoinColumn(name = "player_id") // Chave estrangeira para a tabela Player
    )
    private Set<Player> players = new HashSet<>();

    @ManyToMany(mappedBy = "confirmedTrainings")
    private Set<Player> confirmedPlayers = new HashSet<>();

}

