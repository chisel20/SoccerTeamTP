package com.bstu.brest.dao;

import com.bstu.brest.model.Footballer;

import java.util.List;

public interface FootballerDao {

    List<Footballer> findAll();
    Footballer getFootballerById(Integer footballerId);
    Integer create(Footballer footballer);
    Integer update(Footballer footballer);
    Integer delete(Integer footballerId);
    Integer count();
}
