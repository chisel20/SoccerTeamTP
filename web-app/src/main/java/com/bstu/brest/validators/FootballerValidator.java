package com.bstu.brest.validators;

import com.bstu.brest.model.Footballer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.bstu.brest.model.constants.FootballerConstants.*;

@Component
public class FootballerValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Footballer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"firstName","firstName.isEmpty");
        ValidationUtils.rejectIfEmpty(errors,"lastName","lastName.isEmpty");
        ValidationUtils.rejectIfEmpty(errors,"age","age.isEmpty");
        ValidationUtils.rejectIfEmpty(errors,"salary","salary.isEmpty");
        ValidationUtils.rejectIfEmpty(errors,"joiningDate","joiningDate.somethingWrong");

        Footballer footballer = (Footballer) target;

        if (StringUtils.hasLength(footballer.getFirstName()) && footballer.getFirstName().length() > FOOTBALLER_FIRST_NAME_MAX_SIZE) {
            errors.rejectValue("firstName", "firstName.isSoLong");
        }
        if (StringUtils.hasLength(footballer.getLastName()) && footballer.getLastName().length() > FOOTBALLER_LAST_NAME_MAX_SIZE) {
            errors.rejectValue("lastName", "lastName.isSoLong");
        }
        if ((footballer.getAge()!=null) && footballer.getAge() < FOOTBALLER_AGE_MIN) {
            errors.rejectValue("age", "age.isLessThanZero");
        }
        if ((footballer.getAge()!=null) && footballer.getAge() > FOOTBALLER_AGE_MAX) {
            errors.rejectValue("age", "age.isTooBig");
        }
        if ((footballer.getSalary()!=null) && (footballer.getSalary().compareTo(BigDecimal.ZERO)==-1)) {
            errors.rejectValue("salary", "salary.isLessThanZero");
        }
        if ((footballer.getJoiningDate()!=null) && footballer.getJoiningDate().compareTo(LocalDate.now())>0){
            errors.rejectValue("joiningDate","joiningDate.isTooLate");
        }
    }
}
