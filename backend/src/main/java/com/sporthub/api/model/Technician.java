package com.sporthub.api.model;

import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.model.enums.TechnicianRole;
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
@Table(name = "technicians")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String specialization;

    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechnicianRole technicianRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportType sportType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "headTechnician", fetch = FetchType.LAZY)
    private Team team;
}
