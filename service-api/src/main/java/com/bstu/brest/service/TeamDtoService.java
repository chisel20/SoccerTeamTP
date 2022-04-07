package com.bstu.brest.service;

import com.bstu.brest.model.dto.TeamDto;

import java.util.List;

public interface TeamDtoService {

    /**
     * Get all teams with number of players .
     *
     * @return teams list.
     */
    List<TeamDto> findAllWithNumberOfPlayersAndAvrSalarySql();
}