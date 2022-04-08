package com.bstu.brest.dao;

import com.bstu.brest.model.dto.FootballerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class FootballerDaoDtoJDBCImplTest {

    @InjectMocks
    private FootballerDaoDtoJDBCImpl footballerDaoDto;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Captor
    private ArgumentCaptor<String> captorString;
    @Captor
    private ArgumentCaptor<BeanPropertyRowMapper<FootballerDto>> captorMapper;
    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;
    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }
    @Test
    void findAllWithTeamName() {
        String sql = "findAllWithTeamName";
        ReflectionTestUtils.setField(footballerDaoDto,"findAllWithTeamName",sql);
        FootballerDto footballerDto = new FootballerDto();
        List<FootballerDto> list = Collections.singletonList(footballerDto);
        when(namedParameterJdbcTemplate.query(
                any(),
                ArgumentMatchers.<RowMapper<FootballerDto>>any()
        )).thenReturn(list);

        List<FootballerDto> result = footballerDaoDto.findAllWithTeamName();
        assertThat(result,notNullValue());
        assertThat(result.size(),greaterThan(0));
        assertThat(result,not(empty()));
        assertThat(result.get(0),hasProperty("teamName"));

        verify(namedParameterJdbcTemplate,times(1)).query(
                captorString.capture(),
                captorMapper.capture()
        );

        String sqlString=captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));

        RowMapper<FootballerDto> mapper = captorMapper.getValue();
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(RowMapper.class));
    }
    @Test
    void findAllWithTeamNameWithFilterFromDate() {
        String sql = "findAllWithTeamNameWithFilterFromDate";
        ReflectionTestUtils.setField(footballerDaoDto,"findAllWithTeamNameWithFilterFromDate",sql);
        FootballerDto footballerDto = new FootballerDto();
        footballerDto.setJoiningDate(LocalDate.now().minusMonths(2));
        List<FootballerDto> list = Collections.singletonList(footballerDto);
        when(namedParameterJdbcTemplate.query(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<FootballerDto>>any()
        )).thenReturn(list);
        LocalDate localDate = footballerDto.getJoiningDate().minusMonths(2);
        List<FootballerDto> result = footballerDaoDto.findAllWithTeamNameWithFilterFromDate(localDate);
        assertThat(result,notNullValue());
        assertThat(result.size(),greaterThan(0));
        assertThat(result,not(empty()));
        assertThat(result.get(0),hasProperty("joiningDate"));
        assertThat(footballerDto.getJoiningDate(), greaterThan(localDate));

        verify(namedParameterJdbcTemplate,times(1)).query(
                captorString.capture(),
                captorSource.capture(),
                captorMapper.capture()
        );

        String sqlString=captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));

        SqlParameterSource sqlParameterSource = captorSource.getValue();
        assertThat(sqlParameterSource,notNullValue());
        assertThat(sqlParameterSource.getValue("fromDate"),samePropertyValuesAs(localDate));

        RowMapper<FootballerDto> mapper = captorMapper.getValue();
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(RowMapper.class));
    }
    @Test
    void findAllWithTeamNameWithFilterToDate() {
        String sql = "findAllWithTeamNameWithFilterToDate";
        ReflectionTestUtils.setField(footballerDaoDto,"findAllWithTeamNameWithFilterToDate",sql);
        FootballerDto footballerDto = new FootballerDto();
        footballerDto.setJoiningDate(LocalDate.now().minusMonths(2));
        List<FootballerDto> list = Collections.singletonList(footballerDto);
        when(namedParameterJdbcTemplate.query(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<FootballerDto>>any()
        )).thenReturn(list);
        LocalDate now = LocalDate.now();
        List<FootballerDto> result = footballerDaoDto.findAllWithTeamNameWithFilterToDate(now);
        assertThat(result,notNullValue());
        assertThat(result.size(),greaterThan(0));
        assertThat(result,not(empty()));
        assertThat(result.get(0),hasProperty("joiningDate"));
        assertThat(footballerDto.getJoiningDate(), lessThan(now));

        verify(namedParameterJdbcTemplate,times(1)).query(
                captorString.capture(),
                captorSource.capture(),
                captorMapper.capture()
        );

        String sqlString=captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));

        SqlParameterSource sqlParameterSource = captorSource.getValue();
        assertThat(sqlParameterSource,notNullValue());
        assertThat(sqlParameterSource.getValue("toDate"),samePropertyValuesAs(now));

        RowMapper<FootballerDto> mapper = captorMapper.getValue();
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(RowMapper.class));
    }
    @Test
    void findAllWithTeamNameWithDateFilter() {
        String sql = "findAllWithTeamNameWithDateFilter";
        ReflectionTestUtils.setField(footballerDaoDto,"findAllWithTeamNameWithDateFilter",sql);
        FootballerDto footballerDto = new FootballerDto();
        footballerDto.setJoiningDate(LocalDate.now().minusMonths(2));
        List<FootballerDto> list = Collections.singletonList(footballerDto);
        when(namedParameterJdbcTemplate.query(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<FootballerDto>>any()
        )).thenReturn(list);
        LocalDate fromDate = LocalDate.now().minusMonths(4);
        LocalDate toDate = LocalDate.now();
        List<FootballerDto> result = footballerDaoDto.findAllWithTeamNameWithDateFilter(fromDate,toDate);
        assertThat(result,notNullValue());
        assertThat(result.size(),greaterThan(0));
        assertThat(result,not(empty()));
        assertThat(result.get(0),hasProperty("joiningDate"));
        assertThat(footballerDto.getJoiningDate(), greaterThan(fromDate));
        assertThat(footballerDto.getJoiningDate(), lessThan(toDate));

        verify(namedParameterJdbcTemplate,times(1)).query(
                captorString.capture(),
                captorSource.capture(),
                captorMapper.capture()
        );

        String sqlString=captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));

        SqlParameterSource sqlParameterSource = captorSource.getValue();
        assertThat(sqlParameterSource,notNullValue());
        assertThat(sqlParameterSource.getValue("fromDate"),samePropertyValuesAs(fromDate));
        assertThat(sqlParameterSource.getValue("toDate"),samePropertyValuesAs(toDate));

        RowMapper<FootballerDto> mapper = captorMapper.getValue();
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(RowMapper.class));
    }
}