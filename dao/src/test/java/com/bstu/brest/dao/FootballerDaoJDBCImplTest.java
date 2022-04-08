package com.bstu.brest.dao;

import com.bstu.brest.model.Footballer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FootballerDaoJDBCImplTest {
    @InjectMocks
    private FootballerDaoJDBCImpl footballerDao;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Footballer>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @Captor
    private ArgumentCaptor<String> captorString;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findAll() {
        String sql = "sqlGetAllFootballers";
        ReflectionTestUtils.setField(footballerDao, "sqlGetAllFootballers", sql);
        Footballer footballer = new Footballer();
        List<Footballer> list = Collections.singletonList(footballer);
        when(namedParameterJdbcTemplate.query(
                any(),
                ArgumentMatchers.<RowMapper<Footballer>>any()))
                .thenReturn(list);

        List<Footballer> result = footballerDao.findAll();

        verify(namedParameterJdbcTemplate,times(1))
                .query(
                        captorString.capture(),
                        captorMapper.capture());

        RowMapper<Footballer> mapper = captorMapper.getValue();
        String sqlString = captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(RowMapper.class));
        assertThat(result,notNullValue());
        assertThat(result,not(empty()));
        assertThat(list.size(),equalTo(result.size()));
        for (int i=0;i<list.size();i++){
            assertThat(list.get(i),samePropertyValuesAs(result.get(i)));
        }
    }

    @Test
    public void getFootballerById(){
        String sql = "select * from footballer";
        ReflectionTestUtils.setField(footballerDao,"sqlGetFootballerById",sql);
        Footballer footballer = new Footballer();
        footballer.setFootballerId(2);
        when(namedParameterJdbcTemplate.queryForObject(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Footballer>>any()
        )).thenReturn(footballer);

        Footballer result = footballerDao.getFootballerById(2);
        assertThat(result,notNullValue());
        assertThat(result.getFootballerId(),equalTo(2));

        verify(namedParameterJdbcTemplate,times(1)).queryForObject(
                captorString.capture(),
                captorSource.capture(),
                captorMapper.capture()
        );

        String sqlResult = captorString.getValue();
        assertThat(sqlResult,notNullValue());
        assertThat(sqlResult,equalTo(sql));

        SqlParameterSource sqlParameterSource = captorSource.getValue();
        assertThat(sqlParameterSource,notNullValue());
        assertThat(sqlParameterSource.getValue("footballerId"),equalTo(2));

        RowMapper<Footballer> mapper = captorMapper.getValue();
        assertThat(mapper,notNullValue());
        assertThat(mapper.getClass(),typeCompatibleWith(RowMapper.class));
    }
    @Test
    public void create() {
        String sql = "sqlCreateFootballer";
        ReflectionTestUtils.setField(footballerDao, "sqlCreateFootballer", sql);
        Footballer footballer = new Footballer();
        footballer.setFirstName("Andrey");
        footballer.setLastName("Cheslow");
        footballer.setAge(26);
        footballer.setSalary(BigDecimal.valueOf(123.3f));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());
        when(namedParameterJdbcTemplate.update(
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

        Integer result = footballerDao.create(footballer);
        assertThat(result,notNullValue());
        assertThat(result,equalTo(1));

        verify(namedParameterJdbcTemplate,times(1))
                .update(captorString.capture(), captorSource.capture(), any(), any());

        SqlParameterSource source = captorSource.getValue();
        assertThat(source,notNullValue());
        assertThat(source.getValue("firstName"),equalTo(footballer.getFirstName()));
        assertThat(source.getValue("lastName"),equalTo(footballer.getLastName()));

        String sqlString = captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sql,equalTo(sqlString));
    }
    @Test
    public void update() {
        String sql = "sqlUpdateFootballerById";
        ReflectionTestUtils.setField(footballerDao, "sqlUpdateFootballerById", sql);
        Footballer footballer = new Footballer();
        footballer.setFirstName("Andrey");
        footballer.setLastName("Cheslow");
        footballer.setAge(26);
        footballer.setSalary(BigDecimal.valueOf(123.3f));
        footballer.setTeamId(2);
        footballer.setJoiningDate(LocalDate.now());

        when(namedParameterJdbcTemplate.update(
                any(),
                ArgumentMatchers.<SqlParameterSource>any())
        ).thenReturn(1);

        Integer result = footballerDao.update(footballer);
        assertThat(result,notNullValue());
        assertThat(result,equalTo(1));

        verify(namedParameterJdbcTemplate,times(1))
                .update(captorString.capture(), captorSource.capture());

        String sqlString = captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));

        SqlParameterSource source = captorSource.getValue();
        assertThat(source,notNullValue());
        assertThat(source.getValue("footballerId"),equalTo(footballer.getFootballerId()));
        assertThat(source.getValue("firstName"),equalTo(footballer.getFirstName()));
        assertThat(source.getValue("lastName"),equalTo(footballer.getLastName()));
        assertThat(source.getValue("age"),equalTo(footballer.getAge()));
        assertThat(source.getValue("salary"),samePropertyValuesAs(footballer.getSalary()));
        assertThat(source.getValue("teamId"),equalTo(footballer.getTeamId()));
        assertThat(source.getValue("joiningDate"),samePropertyValuesAs(footballer.getJoiningDate()));
    }
    @Test
    public void delete() {
        String sql = "sqlDeleteFootballerById";
        ReflectionTestUtils.setField(footballerDao, "sqlDeleteFootballerById", sql);
        Footballer footballer = new Footballer();
        footballer.setFootballerId(2);
        when(namedParameterJdbcTemplate.update(
                any(),
                ArgumentMatchers.<SqlParameterSource>any())
        ).thenReturn(1);

        Integer result = footballerDao.delete(footballer.getFootballerId());
        assertThat(result,notNullValue());
        assertThat(result,equalTo(1));

        verify(namedParameterJdbcTemplate,times(1))
                .update(captorString.capture(), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();
        assertThat(source,notNullValue());
        assertThat(source.getValue("footballerId"),equalTo(footballer.getFootballerId()));

        String sqlString = captorString.getValue();
        assertThat(sqlString,notNullValue());
        assertThat(sqlString,equalTo(sql));
    }
    @Test
    public void count(){
        String sql = "sqlFootballersCount";
        ReflectionTestUtils.setField(footballerDao,"sqlFootballersCount",sql);
        when(namedParameterJdbcTemplate.queryForObject(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<Class<Integer>>any()
        )).thenReturn(5);

        Integer result = footballerDao.count();
        assertThat(result,notNullValue());
        assertThat(result,equalTo(5));

        verify(namedParameterJdbcTemplate,times(1))
                .queryForObject(captorString.capture(),captorSource.capture(), eq(Integer.class));

        String sqlResult = captorString.getValue();
        assertThat(sqlResult,notNullValue());
        assertThat(sqlResult,equalTo(sql));

        SqlParameterSource source = captorSource.getValue();
        assertThat(source,notNullValue());
        assertThat(source.getParameterNames().length,equalTo(0));

    }
}
