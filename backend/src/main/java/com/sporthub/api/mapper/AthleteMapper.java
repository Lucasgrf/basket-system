package com.sporthub.api.mapper;

import com.sporthub.api.dto.request.AthleteCreateRequest;
import com.sporthub.api.dto.request.AthleteUpdateRequest;
import com.sporthub.api.dto.response.AthleteResponse;
import com.sporthub.api.model.Athlete;
import com.sporthub.api.model.BasketballAthlete;
import com.sporthub.api.model.SoccerAthlete;
import com.sporthub.api.model.VolleyballAthlete;
import com.sporthub.api.model.enums.SportType;
import org.springframework.stereotype.Component;

@Component
public class AthleteMapper {

    public AthleteResponse toResponse(Athlete athlete) {
        if (athlete == null) {
            return null;
        }

        Long userId = athlete.getUser() != null ? athlete.getUser().getId() : null;
        String photoUrl = athlete.getUser() != null ? athlete.getUser().getPhotoUrl() : null;
        Long teamId = athlete.getTeam() != null ? athlete.getTeam().getId() : null;
        String teamName = athlete.getTeam() != null ? athlete.getTeam().getName() : null;

        String position = null;
        Double height = null;
        Double weight = null;
        String preferredFoot = null;
        Integer shirtNumber = null;
        Double reachHeight = null;
        SportType sportType = SportType.OTHER;

        if (athlete instanceof BasketballAthlete ba) {
            sportType = SportType.BASKETBALL;
            position = ba.getPosition();
            height = ba.getHeight();
            weight = ba.getWeight();
        } else if (athlete instanceof SoccerAthlete sa) {
            sportType = SportType.SOCCER;
            position = sa.getPosition();
            preferredFoot = sa.getPreferredFoot();
            shirtNumber = sa.getShirtNumber();
        } else if (athlete instanceof VolleyballAthlete va) {
            sportType = SportType.VOLLEYBALL;
            position = va.getPosition();
            height = va.getHeight();
            reachHeight = va.getReachHeight();
        }

        return new AthleteResponse(
                athlete.getId(),
                athlete.getNickname(),
                athlete.getBirthDate(),
                sportType,
                userId,
                photoUrl,
                teamId,
                teamName,
                position,
                height,
                weight,
                preferredFoot,
                shirtNumber,
                reachHeight
        );
    }

    public Athlete toEntity(AthleteCreateRequest request) {
        if (request == null) {
            return null;
        }

        return switch (request.sportType()) {
            case BASKETBALL -> BasketballAthlete.builder()
                    .nickname(request.nickname())
                    .birthDate(request.birthDate())
                    .position(request.position())
                    .height(request.height() != null ? request.height() : 0.0)
                    .weight(request.weight() != null ? request.weight() : 0.0)
                    .build();
            case SOCCER -> SoccerAthlete.builder()
                    .nickname(request.nickname())
                    .birthDate(request.birthDate())
                    .position(request.position())
                    .preferredFoot(request.preferredFoot())
                    .shirtNumber(request.shirtNumber() != null ? request.shirtNumber() : 0)
                    .build();
            case VOLLEYBALL -> VolleyballAthlete.builder()
                    .nickname(request.nickname())
                    .birthDate(request.birthDate())
                    .position(request.position())
                    .height(request.height() != null ? request.height() : 0.0)
                    .reachHeight(request.reachHeight() != null ? request.reachHeight() : 0.0)
                    .build();
            default -> throw new IllegalArgumentException("Unsupported sport type for athlete creation");
        };
    }

    public void updateEntity(Athlete athlete, AthleteUpdateRequest request) {
        if (request.nickname() != null) athlete.setNickname(request.nickname());
        if (request.birthDate() != null) athlete.setBirthDate(request.birthDate());

        if (athlete instanceof BasketballAthlete ba) {
            if (request.position() != null) ba.setPosition(request.position());
            if (request.height() != null) ba.setHeight(request.height());
            if (request.weight() != null) ba.setWeight(request.weight());
        } else if (athlete instanceof SoccerAthlete sa) {
            if (request.position() != null) sa.setPosition(request.position());
            if (request.preferredFoot() != null) sa.setPreferredFoot(request.preferredFoot());
            if (request.shirtNumber() != null) sa.setShirtNumber(request.shirtNumber());
        } else if (athlete instanceof VolleyballAthlete va) {
            if (request.position() != null) va.setPosition(request.position());
            if (request.height() != null) va.setHeight(request.height());
            if (request.reachHeight() != null) va.setReachHeight(request.reachHeight());
        }
    }
}
