package com.sporthub.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "soccer_athletes")
@DiscriminatorValue("SOCCER")
public class SoccerAthlete extends Athlete {

    /** GK, CB, LB, RB, CM, CDM, CAM, LW, RW, ST */
    private String position;

    private String preferredFoot;

    private Integer shirtNumber;
}
