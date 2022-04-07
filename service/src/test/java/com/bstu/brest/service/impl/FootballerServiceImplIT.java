package com.bstu.brest.service.impl;

import com.bstu.brest.model.Footballer;
import com.bstu.brest.service.FootballerService;
import com.bstu.brest.service.config.FootballerServiceTestConfig;
import com.bstu.brest.service.exceptions.FootballerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@Import({FootballerServiceTestConfig.class})
@PropertySource({"footballer.properties"})
@Transactional
class FootballerServiceImplIT {

    @Autowired
    FootballerService footballerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCount() {
        assertThat(footballerService, notNullValue());
        Integer num = footballerService.count();
        assertThat(num, notNullValue());
        assertThat(num, greaterThan(0));
        assertThat(num, equalTo(6));

    }

    @Test
    void create() {
        assertThat(footballerService, notNullValue());
        Integer footballerSize = footballerService.count();
        assertThat(footballerSize, notNullValue());
        Footballer footballer = new Footballer();
        footballer.setFirstName("qwe");
        footballer.setLastName("rty");
        footballer.setAge(22);
        footballer.setSalary(BigDecimal.valueOf(123.3));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());
        Integer newFootballerId = footballerService.create(footballer);
        assertThat(newFootballerId, notNullValue());
        assertThat(footballerSize + 1, equalTo(footballerService.count()));
    }

    @Test()
    void createFailed() {
        assertThat(footballerService, notNullValue());
        Integer teamSize = footballerService.count();
        assertThat(teamSize, notNullValue());
        Footballer footballer = new Footballer();
        assertThrows(DataIntegrityViolationException.class, () -> {
            footballerService.create(footballer);
        });
        assertEquals(teamSize, footballerService.count());
    }

    @Test
    void delete() {
        assertThat(footballerService,notNullValue());
        Integer num = footballerService.count();
        assertThat(num,notNullValue());
        Integer deleteFootballer = footballerService.delete(2);
        assertThat(deleteFootballer,notNullValue());
        assertThat(deleteFootballer,greaterThan(0));
        assertThat(num - 1, equalTo(footballerService.count()));
    }

    @Test
    void deleteFailed() {
        assertThat(footballerService,notNullValue());
        Integer num = footballerService.count();
        assertThat(num,notNullValue());
        Integer fail = footballerService.delete(14);
        assertThat(fail,lessThanOrEqualTo(0));
        assertThat(num, equalTo(footballerService.count()));
    }

    @Test
    void update() {
        assertThat(footballerService,notNullValue());
        Footballer footballer = new Footballer();
        footballer.setFootballerId(2);
        footballer.setFirstName("qwe");
        footballer.setLastName("rty");
        footballer.setAge(22);
        footballer.setSalary(BigDecimal.valueOf(123.3).setScale(2,RoundingMode.HALF_DOWN));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());
        Integer upId = footballerService.update(footballer);
        assertThat(upId,notNullValue());
        assertThat(upId ,greaterThan(0));
        assertThat(footballer, samePropertyValuesAs(footballerService.getFootballerById(2)));
    }

    @Test
    void updateFailed() {
        assertThat(footballerService,notNullValue());
        Footballer footballer = new Footballer();
        Integer upId = footballerService.update(footballer);
        assertThat(upId,notNullValue());
        assertThat(upId,lessThanOrEqualTo(0));
        Footballer oldInfFootballer = footballerService.getFootballerById(2);
        footballer.setFootballerId(2);
        footballer.setFirstName("qwe");
        footballer.setLastName("rty");
        footballer.setAge(null);
        footballer.setSalary(BigDecimal.valueOf(123.3).setScale(2,RoundingMode.HALF_DOWN));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());
       assertThrows(DataIntegrityViolationException.class, () -> {
            footballerService.update(footballer);
        });
        assertThat(oldInfFootballer, samePropertyValuesAs(footballerService.getFootballerById(2)));
    }

    @Test
    void getFootballerById() {
        assertThat(footballerService,notNullValue());
        Footballer footballer = new Footballer();
        footballer.setFirstName("qwe");
        footballer.setLastName("rty");
        footballer.setAge(22);
        footballer.setSalary(BigDecimal.valueOf(123.3).setScale(2, RoundingMode.HALF_DOWN));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());
        Integer newFootballerId = footballerService.create(footballer);
        footballer.setFootballerId(newFootballerId);
        Footballer resultFootballer = footballerService.getFootballerById(newFootballerId);
        assertThat(resultFootballer,notNullValue());
        assertThat(footballer, samePropertyValuesAs(resultFootballer));
        assertThat(newFootballerId,equalTo( resultFootballer.getFootballerId()));
    }

    @Test
    void getFootballerByIdFailed() {
        assertThat(footballerService,notNullValue());
        assertThrows(FootballerNotFoundException.class, () -> {
            footballerService.getFootballerById(14);
        });
    }
}
