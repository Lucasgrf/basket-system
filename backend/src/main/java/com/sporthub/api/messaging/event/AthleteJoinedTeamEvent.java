package com.sporthub.api.messaging.event;

/**
 * Published when an athlete is added to a team.
 */
public record AthleteJoinedTeamEvent(
        Long athleteId,
        String athleteUsername,
        Long teamId,
        String teamName,
        String sportType
) {}
