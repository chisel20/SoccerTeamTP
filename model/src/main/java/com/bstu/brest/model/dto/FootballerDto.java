package com.bstu.brest.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FootballerDto {

    private Integer footballerId;
    private String firstName;
    private String lastName;
    private Integer age;
    private BigDecimal salary;
    private Integer teamId;
    private String teamName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    public FootballerDto(){}
    public FootballerDto(Integer footballerId, String firstName, String lastName, Integer age, BigDecimal salary, Integer teamId, String teamName, LocalDate joiningDate) {
        this.footballerId = footballerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.teamId = teamId;
        this.teamName = teamName;
        this.joiningDate = joiningDate;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }


    public Integer getFootballerId() {
        return footballerId;
    }

    public void setFootballerId(Integer footballerId) {
        this.footballerId = footballerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }



    @Override
    public String toString() {
        return "FootballerDto{" +
                "footballerId=" + footballerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", joiningDate=" + joiningDate +
                '}';
    }
}

