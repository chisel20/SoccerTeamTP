package com.bstu.brest.service.config;

import com.bstu.brest.dao.FootballerDao;
import com.bstu.brest.dao.FootballerDaoDto;
import com.bstu.brest.dao.FootballerDaoDtoJDBCImpl;
import com.bstu.brest.dao.FootballerDaoJDBCImpl;
import com.bstu.brest.dbtest.SpringJdbcConfig;
import com.bstu.brest.service.FootballerDtoService;
import com.bstu.brest.service.FootballerService;
import com.bstu.brest.service.impl.FootballerDtoServiceImpl;
import com.bstu.brest.service.impl.FootballerServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FootballerServiceTestConfig extends SpringJdbcConfig {


    @Bean
    FootballerDao footballerDao(){
        return new FootballerDaoJDBCImpl(namedParameterJdbcTemplate());
    }
    @Bean
    FootballerService footballerService(){
        return new FootballerServiceImpl(footballerDao());
    }
    @Bean
    FootballerDaoDto footballerDaoDto(){
        return new FootballerDaoDtoJDBCImpl(namedParameterJdbcTemplate());
    }
    @Bean
    FootballerDtoService footballerDtoService(){
        return new FootballerDtoServiceImpl(footballerDaoDto());
    }
}
