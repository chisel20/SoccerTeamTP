package com.bstu.brest.service;

import com.bstu.brest.model.Footballer;

import java.util.List;

public interface FootballerService {

    List<Footballer> findAll();
    Footballer getFootballerById(Integer footballerId);
    Integer create(Footballer footballer);
    Integer update(Footballer footballer);
    Integer delete(Integer footballerId);
    Integer count();
}
