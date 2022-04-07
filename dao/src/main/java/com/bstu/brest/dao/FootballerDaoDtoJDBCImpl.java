package com.bstu.brest.dao;

import com.bstu.brest.model.dto.FootballerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@PropertySource("footballer.properties")
public class FootballerDaoDtoJDBCImpl implements FootballerDaoDto{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FootballerDaoDtoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${findAllWithTeamName}")
    private String findAllWithTeamName;

    @Value("${findAllWithTeamNameWithFilterFromDate}")
    private String findAllWithTeamNameWithFilterFromDate;

    @Value("${findAllWithTeamNameWithFilterToDate}")
    private String findAllWithTeamNameWithFilterToDate;

    @Value("${findAllWithTeamNameWithDateFilter}")
    private String findAllWithTeamNameWithDateFilter;

    @Override
    public List<FootballerDto> findAllWithTeamName() {
        return namedParameterJdbcTemplate.query(findAllWithTeamName, new FootballerDtoRowMapper());
    }

    @Override
    public List<FootballerDto> findAllWithTeamNameWithFilterFromDate(LocalDate fromDate) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("fromDate",fromDate);
        return namedParameterJdbcTemplate.query(findAllWithTeamNameWithFilterFromDate,sqlParameterSource,new FootballerDtoRowMapper());
    }
    @Override
    public List<FootballerDto> findAllWithTeamNameWithFilterToDate(LocalDate toDate) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("toDate",toDate);
        return namedParameterJdbcTemplate.query(findAllWithTeamNameWithFilterToDate,sqlParameterSource,new FootballerDtoRowMapper());
    }
    @Override
    public List<FootballerDto> findAllWithTeamNameWithDateFilter(LocalDate fromDate, LocalDate toDate) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("toDate",toDate)
                .addValue("fromDate",fromDate);
        return namedParameterJdbcTemplate.query(findAllWithTeamNameWithDateFilter,sqlParameterSource,new FootballerDtoRowMapper());
    }

    private class FootballerDtoRowMapper implements RowMapper<FootballerDto> {

        @Override
        public FootballerDto mapRow(ResultSet resultSet, int i) throws SQLException {
            FootballerDto footballer = new FootballerDto();
            footballer.setFootballerId(resultSet.getInt("footballer_id"));
            footballer.setFirstName(resultSet.getString("firstname"));
            footballer.setLastName(resultSet.getString("lastname"));
            footballer.setAge(resultSet.getInt("age"));
            footballer.setSalary(resultSet.getBigDecimal("salary").setScale(2, RoundingMode.HALF_DOWN));
            footballer.setTeamId(resultSet.getInt("team_id"));
            footballer.setTeamName(resultSet.getString("team_name"));
            footballer.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
            return footballer;
        }
    }
}
