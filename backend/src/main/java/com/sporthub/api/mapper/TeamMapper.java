package com.sporthub.api.mapper;

import com.sporthub.api.dto.request.TeamCreateRequest;
import com.sporthub.api.dto.request.TeamUpdateRequest;
import com.sporthub.api.dto.response.AthleteResponse;
import com.sporthub.api.dto.response.TeamResponse;
import com.sporthub.api.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMapper {

    private final AthleteMapper athleteMapper;

    public TeamResponse toResponse(Team team) {
        if (team == null) {
            return null;
        }

        Long headTechnicianId = team.getHeadTechnician() != null ? team.getHeadTechnician().getId() : null;
        String headTechnicianName = team.getHeadTechnician() != null ? team.getHeadTechnician().getNickname() : null;

        List<AthleteResponse> athletes = team.getAthletes() != null
                ? team.getAthletes().stream().map(athleteMapper::toResponse).collect(Collectors.toList())
                : Collections.emptyList();

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getAddress(),
                team.getGym(),
                team.getFoundation(),
                team.getEmailContact(),
                team.getPhoneContact(),
                team.getSportType(),
                headTechnicianId,
                headTechnicianName,
                athletes
        );
    }

    public Team toEntity(TeamCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Team.builder()
                .name(request.name())
                .address(request.address())
                .gym(request.gym())
                .foundation(request.foundation())
                .emailContact(request.emailContact())
                .phoneContact(request.phoneContact())
                .sportType(request.sportType())
                .build();
    }

    public void updateEntity(Team team, TeamUpdateRequest request) {
        if (request.name() != null) team.setName(request.name());
        if (request.address() != null) team.setAddress(request.address());
        if (request.gym() != null) team.setGym(request.gym());
        if (request.foundation() != null) team.setFoundation(request.foundation());
        if (request.emailContact() != null) team.setEmailContact(request.emailContact());
        if (request.phoneContact() != null) team.setPhoneContact(request.phoneContact());
        if (request.sportType() != null) team.setSportType(request.sportType());
    }
}
