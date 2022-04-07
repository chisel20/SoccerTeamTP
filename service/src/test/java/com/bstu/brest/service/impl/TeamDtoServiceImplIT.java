package com.bstu.brest.service.impl;

import com.bstu.brest.model.Team;
import com.bstu.brest.model.dto.TeamDto;
import com.bstu.brest.service.TeamDtoService;
import com.bstu.brest.service.TeamService;
import com.bstu.brest.service.config.TeamServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@Import({TeamServiceTestConfig.class})
@PropertySource({"team.properties"})
@Transactional
class TeamDtoServiceImplIT {

    @Autowired
    TeamDtoService teamDtoService;

    @Autowired
    TeamService teamService;

    @Test
    void findAllWithNumberOfPlayersAndAvrSalarySql() {
        Team team = new Team();
        team.setTeamName("qweqwe");
        Integer newTeamId = teamService.create(team);
        List<TeamDto> teams = teamDtoService.findAllWithNumberOfPlayersAndAvrSalarySql();
        assertThat(teams, notNullValue());
        assertThat(teams, hasSize(6));
        List<TeamDto> listWithAvgSalary = teams.stream().filter(p->p.getAvgSalary()!=null).collect(Collectors.toList()).stream().filter(n-> n.getAvgSalary().compareTo(BigDecimal.ZERO)>0).collect(Collectors.toList());
        listWithAvgSalary.addAll(teams.stream().filter(p->p.getAvgSalary()==null).collect(Collectors.toList()));
        assertThat(listWithAvgSalary,hasSize(6));
        List<TeamDto> listWithNumberOfFootballers = teams.stream().filter(n-> n.getNumberOfFootballers()>=0).collect(Collectors.toList());
        assertThat(listWithNumberOfFootballers,hasSize(6));
    }

    @Test
    void findAllWithNumberOfPlayersAndAvrSalarySqlFailed() {
        Team team = new Team();
        team.setTeamName("qweqwe");
        Integer newTeamId = teamService.create(team);
        List<TeamDto> teams = teamDtoService.findAllWithNumberOfPlayersAndAvrSalarySql();
        assertThat(teams, notNullValue());
        assertThat(teams, hasSize(6));
        List<TeamDto> listWithAvgSalary = teams.stream().filter(p->p.getAvgSalary()!=null).collect(Collectors.toList());
        assertThat(listWithAvgSalary,not(hasSize(6)));
        List<TeamDto> listWithNumberOfFootballers = teams.stream().filter(n-> n.getNumberOfFootballers()>=0).collect(Collectors.toList());
        assertThat(listWithNumberOfFootballers,hasSize(6));
    }
}