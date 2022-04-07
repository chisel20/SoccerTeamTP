package com.bstu.brest.service.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class TeamNotFoundException extends EmptyResultDataAccessException {
    public TeamNotFoundException(Integer teamId) {
        super("Can't find team!(id: " + teamId + ")", 1);
    }
}
