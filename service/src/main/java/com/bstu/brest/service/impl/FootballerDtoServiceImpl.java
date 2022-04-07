package com.bstu.brest.service.impl;

import com.bstu.brest.dao.FootballerDaoDto;
import com.bstu.brest.service.FootballerDtoService;
import com.bstu.brest.model.dto.FootballerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class FootballerDtoServiceImpl implements FootballerDtoService {

    private final FootballerDaoDto footballerDaoDto;

    public FootballerDtoServiceImpl(FootballerDaoDto footballerDaoDto) {
        this.footballerDaoDto = footballerDaoDto;
    }

    @Override
    public List<FootballerDto> findAllWithTeamName() {
        return this.footballerDaoDto.findAllWithTeamName();
    }

    @Override
    public List<FootballerDto> findAllWithTeamNameWithDateFilter(LocalDate fromDate,LocalDate toDate) {
        if (fromDate!=null && toDate!=null) {
            return this.footballerDaoDto.findAllWithTeamNameWithDateFilter(fromDate,toDate);
        } else if((fromDate==null) && (toDate!=null)){
            return this.footballerDaoDto.findAllWithTeamNameWithFilterToDate(toDate);
        }else if((fromDate!=null)){
            return this.footballerDaoDto.findAllWithTeamNameWithFilterFromDate(fromDate);
        } else return this.footballerDaoDto.findAllWithTeamName();
    }
}
