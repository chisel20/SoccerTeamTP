package com.bstu.brest.service.impl;

import com.bstu.brest.model.Team;
import com.bstu.brest.service.TeamService;
import com.bstu.brest.service.config.TeamServiceTestConfig;
import com.bstu.brest.service.exceptions.TeamNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@Import({TeamServiceTestConfig.class})
@PropertySource({"team.properties"})
@Transactional
class TeamServiceImplIT {

    @Autowired
    TeamService teamService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCount(){
        assertThat(teamService,notNullValue());
        Integer num = teamService.count();
        assertThat(num,notNullValue());
        assertThat(num,greaterThan(0));
        assertThat(num,equalTo(5));

    }

    @Test
    void create() {
        assertThat(teamService,notNullValue());
        Integer teamSize = teamService.count();
        assertThat(teamSize,notNullValue());
        Team team = new Team();
        team.setTeamName("qwer");
        Integer newTeamId = teamService.create(team);
        assertThat(newTeamId,notNullValue());
        assertThat(teamSize+1,equalTo(teamService.count()));
    }

    @Test()
    void createFailed() {
        assertThat(teamService,notNullValue());
        Integer teamSize = teamService.count();
        assertThat(teamSize,notNullValue());
        Team team = new Team();
        assertThrows(DataIntegrityViolationException.class, () -> {
            teamService.create(team);
        });
        assertThat(teamSize,equalTo(teamService.count()));
    }

    @Test
    void tryToCreateSameTeam() {
        assertThat(teamService,notNullValue());
        Team team = new Team();
        team.setTeamName("qweqwe");

        assertThrows(IllegalArgumentException.class, () -> {
            teamService.create(team);
            teamService.create(team);
        });
    }

    @Test
    void delete(){
        assertThat(teamService,notNullValue());
        Team team = new Team();
        team.setTeamName("qwe");
        Integer creatingTeam = teamService.create(team);
        assertThat(creatingTeam,notNullValue());
        Integer num = teamService.count();
        assertThat(num,notNullValue());
        Integer deletingTeam = teamService.delete(creatingTeam);
        assertThat(deletingTeam,notNullValue());
        assertThat(deletingTeam,greaterThan(0));
        assertThat(num-1,equalTo(teamService.count()));
    }

    @Test
    void deleteFailed(){
        assertThat(teamService,notNullValue());
        Integer num = teamService.count();
        assertThat(num,notNullValue());
        Integer fail = teamService.delete(14);
        assertThat(fail,lessThanOrEqualTo(0));
        assertThrows(DataIntegrityViolationException.class, () -> {
            teamService.delete(1);
        });
        assertThat(num,equalTo(teamService.count()));
    }

    @Test
    void update(){
        assertThat(teamService,notNullValue());
        Team team = new Team();
        team.setTeamId(2);
        team.setTeamName("qwerqwer");
        Integer upId = teamService.update(team);
        assertThat(upId,notNullValue());
        assertThat(upId,greaterThan(0));
        assertThat(team,samePropertyValuesAs(teamService.getTeamById(2)));
        assertThat(team.getTeamId(),equalTo(teamService.getTeamById(2).getTeamId()));
    }

    @Test
    void updateFailed(){
        assertThat(teamService,notNullValue());
        Team team = new Team();
        Integer upId = teamService.update(team);
        assertThat(upId,notNullValue());
        assertThat(upId,lessThanOrEqualTo(0));
        Team oldTeam = teamService.getTeamById(2);
        team.setTeamId(2);
        team.setTeamName("qweqweqweqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        assertThrows(DataIntegrityViolationException.class, () -> {
            teamService.update(team);
        });
        assertThat(oldTeam,samePropertyValuesAs(teamService.getTeamById(2)));
    }

    @Test
    void getTeamById(){
        assertThat(teamService,notNullValue());
        Team team = new Team();
        team.setTeamName("qweqwe");
        Integer newTeamId = teamService.create(team);
        team.setTeamId(newTeamId);
        Team resultTeam = teamService.getTeamById(newTeamId);
        assertThat(resultTeam,notNullValue());
        assertThat(team,samePropertyValuesAs(resultTeam));
        assertThat(newTeamId,equalTo(resultTeam.getTeamId()));
    }
    @Test
    void getTeamByIdFailed(){
        assertThat(teamService,notNullValue());
        assertThrows(TeamNotFoundException.class, () -> {
            teamService.getTeamById(14);
        });
    }

}