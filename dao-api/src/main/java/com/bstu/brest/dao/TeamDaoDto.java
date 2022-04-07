package com.bstu.brest.dao;


import com.bstu.brest.model.dto.TeamDto;
import java.util.List;

public interface TeamDaoDto {

    /**
     * Get all teams with number of players .
     *
     * @return teams list.
     */
    List<TeamDto> findAllWithNumberOfPlayersAndAvrSalarySql();

}
