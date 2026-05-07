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
@Table(name = "volleyball_athletes")
@DiscriminatorValue("VOLLEYBALL")
public class VolleyballAthlete extends Athlete {

    /** Setter, Outside Hitter, Middle Blocker, Opposite, Libero */
    private String position;

    /** Height in centimeters */
    private Double height;

    /** Reach height in centimeters */
    private Double reachHeight;
}
