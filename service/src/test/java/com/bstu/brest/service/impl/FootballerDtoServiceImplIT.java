package com.bstu.brest.service.impl;

import com.bstu.brest.model.Footballer;
import com.bstu.brest.model.dto.FootballerDto;
import com.bstu.brest.service.FootballerDtoService;
import com.bstu.brest.service.FootballerService;
import com.bstu.brest.service.config.FootballerServiceTestConfig;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@Import({FootballerServiceTestConfig.class})
@PropertySource({"footballer.properties"})
@Transactional
class FootballerDtoServiceImplIT {

    @Autowired
    FootballerDtoService footballerDtoService;

    @Autowired
    FootballerService footballerService;

    @Test
    void findAllWithTeamName() {
        List<FootballerDto> footballers = footballerDtoService.findAllWithTeamName();
        assertThat(footballers,notNullValue());
        assertThat(footballers,hasSize(6));
        List<FootballerDto> footballersWithTeamName = footballers.stream().filter(n->n.getTeamName()!=null).collect(Collectors.toList());
        assertThat(footballersWithTeamName,hasSize(6));
    }

    @Test
    void findAllWithTeamNameWithDateFilter() {
        Footballer footballer = new Footballer();
        footballer.setAge(22);
        footballer.setFirstName("qwe");
        footballer.setLastName("rty");
        footballer.setSalary(BigDecimal.valueOf(123.3).setScale(2, RoundingMode.HALF_DOWN));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());
        footballerService.create(footballer);
        LocalDate fromDate = LocalDate.parse("2011-12-11");
        LocalDate toDate = LocalDate.parse("2020-12-11");
        List<FootballerDto> footballers = footballerDtoService.findAllWithTeamNameWithDateFilter(fromDate,toDate);
        assertThat(footballers,notNullValue());
        assertThat(footballers,hasSize(6));
        List<FootballerDto> footballersWithDate = footballerDtoService.findAllWithTeamName();
        assertThat(footballersWithDate,hasSize(7));
        List<FootballerDto> resultList = footballersWithDate.stream().filter(p->p.getJoiningDate().compareTo(fromDate)>0).collect(Collectors.toList());
        resultList = resultList.stream().filter(n->n.getJoiningDate().compareTo(toDate)<0).collect(Collectors.toList());
        assertThat(resultList,hasSize(6));
        List<FootballerDto> footballersWithTeamName = footballers.stream().filter(n->n.getTeamName()!=null).collect(Collectors.toList());
        assertThat(footballersWithTeamName,hasSize(6));
       /* FootballerDto a = new FootballerDto();
        a.setJoiningDate(LocalDate.now());
        a.setFootballerId(12);
        a.setFirstName("qwwe0");
        a.setLastName("assd");
        a.setAge(22);
        a.setTeamId(3);
        a.setTeamName("ManUtd");
        a.setSalary(BigDecimal.valueOf(1222.3).setScale(2,RoundingMode.HALF_DOWN));*/
       // footballers.get(5).setLastName("qweqwe");
        assertThat(footballers.size(),equalTo(resultList.size()));
        for (int i=0;i<footballers.size();i++){
            assertThat(footballers.get(i),samePropertyValuesAs(resultList.get(i)));
        }
    }
}