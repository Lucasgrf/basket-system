package com.sporthub.api.model;

import com.sporthub.api.model.enums.SportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teams")
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

    @Column(nullable = false)
    private LocalDate foundation;

    @Column(nullable = false, unique = true)
    private String emailContact;

    @Column(nullable = false, unique = true)
    private String phoneContact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportType sportType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_technician_id")
    private Technician headTechnician;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TrainingSession> trainingSessions = new HashSet<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Athlete> athletes = new HashSet<>();
}
