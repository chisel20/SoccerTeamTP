package com.bstu.brest.service.config;

import com.bstu.brest.dao.*;
import com.bstu.brest.dbtest.SpringJdbcConfig;
import com.bstu.brest.service.TeamDtoService;
import com.bstu.brest.service.TeamService;
import com.bstu.brest.service.impl.TeamDtoServiceImpl;
import com.bstu.brest.service.impl.TeamServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TeamServiceTestConfig extends SpringJdbcConfig {

    @Bean
    TeamDao teamDao(){
        return new TeamDaoJDBCImpl(namedParameterJdbcTemplate());
    }
    @Bean
    TeamService teamService(){
        return new TeamServiceImpl(teamDao());
    }
    @Bean
    TeamDaoDto teamDaoDto(){
        return new TeamDaoDtoJDBCImpl(namedParameterJdbcTemplate());
    }
    @Bean
    TeamDtoService teamDtoService(){
        return new TeamDtoServiceImpl(teamDaoDto());
    }

}
