package com.bstu.brest.dao;

import com.bstu.brest.model.Team;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
@PropertySource("team.properties")
public class TeamDaoJDBCImpl implements TeamDao{
    public TeamDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_TEAMS_COUNT}")
    private String sqlTeamsCount;

    @Value("${SQL_ALL_TEAMS}")
    private String sqlGetAllTeams;

    @Value("${SQL_TEAM_BY_ID}")
    private String sqlGetTeamById;

    @Value("${SQL_CHECK_UNIQUE_TEAM_NAME}")
    private String sqlCheckUniqueTeamName;

    @Value("${SQL_CREATE_TEAM}")
    private String sqlCreateTeam;

    @Value("${SQL_UPDATE_TEAM_NAME}")
    private String sqlUpdateTeamName;

    @Value("${SQL_DELETE_TEAM_BY_ID}")
    private String sqlDeleteTeamById;

    @Override
    public List<Team> findAll() {
        return namedParameterJdbcTemplate.query(sqlGetAllTeams, new TeamRowMapper());
    }

    @Override
    public Team getTeamById(Integer teamId){
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("teamId", teamId);
        return namedParameterJdbcTemplate.queryForObject(sqlGetTeamById, sqlParameterSource, new TeamRowMapper());
    }

    @Override
    public Integer create(Team team) {
        if (!isUniqueTeamName(team.getTeamName())) {
            throw new IllegalArgumentException("Team with the same name already exists in DB.");
        }

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("teamName", team.getTeamName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateTeam, sqlParameterSource, keyHolder, new String[] {"team_id"});
        return ((Integer) keyHolder.getKey());
    }

    @Override
    public boolean isUniqueTeamName(String teamName) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("teamName", teamName);
        return namedParameterJdbcTemplate.queryForObject(sqlCheckUniqueTeamName, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Team team) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("teamName", team.getTeamName())
                        .addValue("teamId", team.getTeamId());
        return namedParameterJdbcTemplate.update(sqlUpdateTeamName, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer teamId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("teamId", teamId);
        return namedParameterJdbcTemplate.update(sqlDeleteTeamById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        return namedParameterJdbcTemplate
                .queryForObject(sqlTeamsCount, new MapSqlParameterSource(), Integer.class);
    }

    private class TeamRowMapper implements RowMapper<Team> {

        @Override
        public Team mapRow(ResultSet resultSet, int i) throws SQLException {
            Team team = new Team();
            team.setTeamId(resultSet.getInt("team_id"));
            team.setTeamName(resultSet.getString("team_name"));
            return team;
        }
    }
}
