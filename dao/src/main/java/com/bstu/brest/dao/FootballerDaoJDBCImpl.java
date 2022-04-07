package com.bstu.brest.dao;

import com.bstu.brest.model.Footballer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@PropertySource("footballer.properties")
public class FootballerDaoJDBCImpl implements FootballerDao{

    public FootballerDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_ALL_FOOTBALLERS}")
    private String sqlGetAllFootballers;

    @Value("${SQL_FOOTBALLER_BY_ID}")
    private String sqlGetFootballerById;

    @Value("${SQL_UPDATE_FOOTBALLER_BY_ID}")
    private  String sqlUpdateFootballerById;

    @Value("${SQL_CREATE_FOOTBALLER}")
    private  String sqlCreateFootballer;

    @Value("${SQL_DELETE_FOOTBALLER_BY_ID}")
    private  String sqlDeleteFootballerById;

    @Value("${SQL_FOOTBALLERS_COUNT}")
    private  String sqlFootballersCount;

    @Override
    public List<Footballer> findAll() {
        return namedParameterJdbcTemplate.query(sqlGetAllFootballers, new FootballerRowMapper());
    }

    @Override
    public Footballer getFootballerById(Integer footballerId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("footballerId", footballerId);
        return namedParameterJdbcTemplate.queryForObject(sqlGetFootballerById, sqlParameterSource, new FootballerRowMapper());
    }

    @Override
    public Integer create(Footballer footballer) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("firstName", footballer.getFirstName())
                        .addValue("lastName",footballer.getLastName())
                        .addValue("age",footballer.getAge())
                        .addValue("salary",footballer.getSalary())
                        .addValue("teamId",footballer.getTeamId())
                        .addValue("joiningDate",footballer.getJoiningDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateFootballer, sqlParameterSource, keyHolder, new String[] {"footballer_id"});
        return ((Integer) keyHolder.getKey());
    }

    @Override
    public Integer update(Footballer footballer) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("footballerId",footballer.getFootballerId())
                .addValue("firstName",footballer.getFirstName())
                .addValue("lastName",footballer.getLastName())
                .addValue("age",footballer.getAge())
                .addValue("salary",footballer.getSalary())
                .addValue("teamId",footballer.getTeamId())
                .addValue("joiningDate",footballer.getJoiningDate());
        return namedParameterJdbcTemplate.update(sqlUpdateFootballerById,sqlParameterSource);
    }

    @Override
    public Integer delete(Integer footballerId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("footballerId",footballerId);
        return namedParameterJdbcTemplate.update(sqlDeleteFootballerById,sqlParameterSource);
    }

    @Override
    public Integer count() {
        return namedParameterJdbcTemplate.queryForObject(sqlFootballersCount, new MapSqlParameterSource(),Integer.class);
    }

    private class FootballerRowMapper implements RowMapper<Footballer> {

        @Override
        public Footballer mapRow(ResultSet resultSet, int i) throws SQLException {
            Footballer footballer = new Footballer();
            footballer.setFootballerId(resultSet.getInt("footballer_id"));
            footballer.setFirstName(resultSet.getString("firstname"));
            footballer.setLastName(resultSet.getString("lastname"));
            footballer.setAge(resultSet.getInt("age"));
            footballer.setSalary(resultSet.getBigDecimal("salary").setScale(2, RoundingMode.HALF_DOWN));
            footballer.setTeamId(resultSet.getInt("team_id"));
            footballer.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
            return footballer;
        }
    }
}
