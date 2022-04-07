package com.bstu.brest.service;

import com.bstu.brest.model.Team;

import java.util.List;

public interface TeamService {

    /**
     * Find all teams.
     *
     * @return teams list.
     */
    List<Team> findAll();

    /**
     * Find team by Id.
     *
     * @param teamId team Id.
     * @return team
     */
    Team getTeamById(Integer teamId);

    /**
     * Persist new team.
     *
     * @param team team.
     * @return persisted team id.
     */
    Integer create(Team team);

    /**
     * Update team.
     *
     * @param team team.
     * @return number of updated records in the database.
     */
    Integer update(Team team);

    /**
     * Delete team.
     *
     * @param teamId team id.
     * @return number of updated records in the database.
     */
    Integer delete(Integer teamId);

    /**
     * Count teams.
     *
     * @return quantity of the teams.
     */
    Integer count();

}
