package com.bstu.brest.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class DefaultExceptionHandlingControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(NOT_FOUND)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {

        ModelAndView mav = new ModelAndView();

        mav.addObject("exception", ex);
        mav.addObject("errorMessage",ex.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.setViewName("error");
        return mav;
    }

}
