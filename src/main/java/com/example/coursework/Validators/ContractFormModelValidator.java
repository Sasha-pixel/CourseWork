package com.example.coursework.Validators;

import com.example.coursework.Data.Entities.ContractFormModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContractFormModelValidator implements Validator {

    private Pattern pattern;

    private Matcher matcher;

    private static final String CAR_NUMBER_PATTERN = "[А-Я]\\d{3}[А-Я]{2}\\d{2,3}";
    private static final String VIN_PATTERN = "^[A-HJ-NPR-Za-hj-npr-z\\d]{8}[\\dX][A-HJ-NPR-Za-hj-npr-z\\d]{2}\\d{6}$"; //1ftfw1et4bfc45903 example
    private static final String LICENSE_NUMBER_PATTERN = "^[A-Z](?:\\d[- ]*){14}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return ContractFormModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContractFormModel contractFormModel = (ContractFormModel) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "carNumber", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearOfManufacture", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "power", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicleIdentificationNumber", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "licenseNumber", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "drivingExperience", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "обязательно к заполнению");
        if (contractFormModel.getDrivingExperience() > 70)
            errors.rejectValue("drivingExperience", "вы слишком старый");
        if (new Date().getYear() - contractFormModel.getYearOfManufacture() > 15)
            errors.rejectValue("yearOfManufacture", "ваш автомобиль слишком старый");
        if (!validateCarNumber(contractFormModel.getCarNumber()))
            errors.rejectValue("carNumber", "неправильный формат");
        if (!validateVin(contractFormModel.getVehicleIdentificationNumber()))
            errors.rejectValue("vehicleIdentificationNumber", "неправильный формат");
        if (!validateLicenseNumber(contractFormModel.getLicenseNumber()))
            errors.rejectValue("licenseNumber", "неправильный формат");
    }

    public boolean validateCarNumber(String carNumber) {
        pattern = Pattern.compile(CAR_NUMBER_PATTERN);
        matcher = pattern.matcher(carNumber);
        return matcher.matches();
    }

    public boolean validateVin(String vehicleIdentificationNumber) {
        pattern = Pattern.compile(VIN_PATTERN);
        matcher = pattern.matcher(vehicleIdentificationNumber);
        return matcher.matches();
    }

    public boolean validateLicenseNumber(String licenseNumber) {
        pattern = Pattern.compile(LICENSE_NUMBER_PATTERN);
        matcher = pattern.matcher(licenseNumber);
        return matcher.matches();
    }
}
