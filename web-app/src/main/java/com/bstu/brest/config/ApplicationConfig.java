package com.bstu.brest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ApplicationConfig {

   /* @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

   private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ApplicationConfig(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }*/

   /* @Bean
    TeamService teamService() {
        return new TeamServiceImpl(new TeamDaoJDBCImpl(namedParameterJdbcTemplate));
    }*/
    /*@Bean
    TeamDao teamDao(){
        return new TeamDaoJDBCImpl(namedParameterJdbcTemplate);
    }*/

}

