package com.bstu.brest.dao;

import com.bstu.brest.model.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TeamDaoJDBCImplTest {

    @InjectMocks
    private TeamDaoJDBCImpl teamDao;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Mock
    private GeneratedKeyHolder generatedKeyHolder;
    @Captor
    private ArgumentCaptor<RowMapper<Team>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @Captor
    private ArgumentCaptor<String> captorString;
    @Captor
    private ArgumentCaptor<KeyHolder> captorKeyHolder;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findAll() {
        String sql = "select";
        ReflectionTestUtils.setField(teamDao, "sqlGetAllTeams", sql);
        Team team = new Team();
        List<Team> list = Collections.singletonList(team);
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Team>>any()))
                .thenReturn(list);
        List<Team> result = teamDao.findAll();
        Mockito.verify(namedParameterJdbcTemplate,times(1)).query(captorString.capture(), captorMapper.capture());
        RowMapper<Team> mapper = captorMapper.getValue();
        assertThat(sql,equalTo(captorString.getValue()));
        assertThat(mapper,notNullValue());
        assertThat(mapper.toString(),stringContainsInOrder(Arrays.asList("Team","RowMapper")));
        assertThat(result,notNullValue());
        assertThat(result,not(empty()));
        assertThat(team,samePropertyValuesAs(result.get(0)));
    }
    @Test
    public void getTeamById() {
        String sql = "get";
        ReflectionTestUtils.setField(teamDao, "sqlGetTeamById", sql);
        int id = 0;
        Team team = new Team();

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Team>>any())
        ).thenReturn(team);

        Team result = teamDao.getTeamById(id);

        Mockito.verify(namedParameterJdbcTemplate,times(1))
                .queryForObject(captorString.capture(), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Team> mapper = captorMapper.getValue();

        assertThat(sql,equalTo(captorString.getValue()));
        assertThat(source,notNullValue());
        assertThat(source.getValue("teamId"),equalTo(id));
        assertThat(mapper,notNullValue());
        assertThat(mapper.toString(),stringContainsInOrder(Arrays.asList("Team","RowMapper")));
        assertThat(result,notNullValue());
        assertThat(team,samePropertyValuesAs(result));
    }
    @Test
    public void create() {
        String sql = "update";
        String sql2 = "select-check";
        ReflectionTestUtils.setField(teamDao, "sqlCreateTeam", sql);
        ReflectionTestUtils.setField(teamDao, "sqlCheckUniqueTeamName", sql2);
        Team team = new Team();
        team.setTeamName("MCity");
        Mockito.when(namedParameterJdbcTemplate.update(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<KeyHolder>any(),
                ArgumentMatchers.<String[]>any())
        ).thenAnswer(invocation ->  {
            Object[] args = invocation.getArguments();
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("", 1);
            ((KeyHolder)args[2]).getKeyList().add(keyMap);
            return null;
        });

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<Class<Object>>any())
        ).thenReturn(0);

        Integer result = teamDao.create(team);
        Mockito.verify(namedParameterJdbcTemplate,times(1))
                .update(captorString.capture(), captorSource.capture(), any(), any());

        SqlParameterSource source = captorSource.getValue();
        String sqlString = captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sql,equalTo(sqlString));
        assertThat(source,notNullValue());
        assertThat(source.getValue("teamName"),equalTo(team.getTeamName()));
        assertThat(result,notNullValue());
        assertThat(result,equalTo(1));
    }
    @Test
    public void creatWithException(){
        String sql = "select-check";
        ReflectionTestUtils.setField(teamDao, "sqlCheckUniqueTeamName", sql);
        Team team = new Team();
        team.setTeamName("MCity");

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<Class<Object>>any())
        ).thenReturn(1);
        assertThrows(IllegalArgumentException.class,()->{
            teamDao.create(team);
        });
    }

    public void update() {
        String sql = "get";
        ReflectionTestUtils.setField(teamDao, "sqlUpdateTeamName", sql);
        Team team = new Team();
        team.setTeamId(2);
        team.setTeamName("qwe");
        Mockito.when(namedParameterJdbcTemplate.update(
                any(),
                ArgumentMatchers.<SqlParameterSource>any())
        ).thenReturn(1);

        Integer result = teamDao.update(team);

        Mockito.verify(namedParameterJdbcTemplate,times(1))
                .update(captorString.capture(), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();
        String sqlString = captorString.getValue();

        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));
        assertThat(source,notNullValue());
        assertThat(source.getValue("teamId"),equalTo(team.getTeamId()));
        assertThat(source.getValue("teamName"),equalTo(team.getTeamName()));
        assertThat(result,notNullValue());
        assertThat(team,equalTo(1));
    }
}