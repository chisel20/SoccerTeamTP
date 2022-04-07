package com.bstu.brest.service;

import com.bstu.brest.model.dto.FootballerDto;

import java.time.LocalDate;
import java.util.List;

public interface FootballerDtoService {

    /**
     * Get all footballers with team name .
     *
     * @return footballers list.
     */
    List<FootballerDto> findAllWithTeamName();

    /**
     * Get all footballers with team name and filter date.
     *
     * @return footballers list.
     */
    List<FootballerDto> findAllWithTeamNameWithDateFilter(LocalDate fromDate,LocalDate toDate);
}
