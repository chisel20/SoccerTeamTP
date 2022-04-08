package com.bstu.brest.dao;

import com.bstu.brest.model.dto.TeamDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.swing.tree.RowMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class TeamDaoDtoJDBCImplTest {

    @InjectMocks
    private TeamDaoDtoJDBCImpl teamDaoDto;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Captor
    private ArgumentCaptor<String> captorString;
    @Captor
    private ArgumentCaptor<BeanPropertyRowMapper<TeamDto>> captorMapper;
    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }
    @Test
    void findAllWithNumberOfPlayersAndAvrSalarySql() {
        String sql = "findAllWithNumberOfPlayersAndAvrSalarySql";
        ReflectionTestUtils.setField(teamDaoDto,"findAllWithNumberOfPlayersAndAvrSalarySql",sql);
        TeamDto teamDto = new TeamDto();
        List<TeamDto> list = Collections.singletonList(teamDto);
        when(namedParameterJdbcTemplate.query(
                any(),
                ArgumentMatchers.<BeanPropertyRowMapper<TeamDto>>any()
        )).thenReturn(list);

        List<TeamDto> result = teamDaoDto.findAllWithNumberOfPlayersAndAvrSalarySql();
        assertThat(result,notNullValue());
        assertThat(result.size(),greaterThan(0));
        assertThat(result,not(empty()));

        verify(namedParameterJdbcTemplate,times(1)).query(
                captorString.capture(),
                captorMapper.capture()
        );

        String sqlString=captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));

        BeanPropertyRowMapper<TeamDto> mapper = captorMapper.getValue();
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(BeanPropertyRowMapper.class));


    }
}