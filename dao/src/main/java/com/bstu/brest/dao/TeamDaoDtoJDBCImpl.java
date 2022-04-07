package com.bstu.brest.dao;

import com.bstu.brest.model.dto.TeamDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Department DTO DAO implementation.
 */
@Repository
@PropertySource("team.properties")
public class TeamDaoDtoJDBCImpl implements TeamDaoDto {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${findAllWithNumberOfPlayersAndAvrSalarySql}")
    private String findAllWithNumberOfPlayersAndAvrSalarySql;

    public TeamDaoDtoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<TeamDto> findAllWithNumberOfPlayersAndAvrSalarySql() {

        List<TeamDto> teams = namedParameterJdbcTemplate.query(findAllWithNumberOfPlayersAndAvrSalarySql,
                BeanPropertyRowMapper.newInstance(TeamDto.class));

            for (TeamDto team : teams) {
                if (team.getAvgSalary() != null){
                    BigDecimal x = team.getAvgSalary().setScale(2, RoundingMode.HALF_DOWN);
                    team.setAvgSalary(x);
                }
            }

        return teams;
    }

}
