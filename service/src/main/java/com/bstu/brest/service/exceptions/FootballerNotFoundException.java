package com.bstu.brest.service.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class FootballerNotFoundException extends EmptyResultDataAccessException {
    public FootballerNotFoundException(Integer footballerId) {
        super("Can't find footballer!(id: "+footballerId+")", 1);
    }
}
