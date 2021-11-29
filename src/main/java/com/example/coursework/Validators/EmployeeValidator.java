package com.example.coursework.Validators;

import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmployeeValidator implements Validator {

    @Autowired
    private EmployeeService employeeService;

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "обязательно к заполнению");
        if (!validateName(employee.getName()))
            errors.rejectValue("name", "неправильный формат имени или фамилии");
    }

    public boolean validateName(String name) {
        pattern = Pattern.compile(RegexPatterns.NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
