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
@Table(name = "basketball_athletes")
@DiscriminatorValue("BASKETBALL")
public class BasketballAthlete extends Athlete {

    /** PG, SG, SF, PF, C */
    private String position;

    /** Height in centimeters */
    private Double height;

    /** Weight in kilograms */
    private Double weight;
}
