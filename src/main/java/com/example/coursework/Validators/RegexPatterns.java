package com.example.coursework.Validators;

public class RegexPatterns {
    static final String NAME_PATTERN = "^[а-яА-Я_]+( [а-яА-Я_]+)*$";
    static final String CAR_NUMBER_PATTERN = "[А-Я]\\d{3}[А-Я]{2}\\d{2,3}";
    static final String VIN_PATTERN = "^[A-HJ-NPR-Za-hj-npr-z\\d]{8}[\\dX][A-HJ-NPR-Za-hj-npr-z\\d]{2}\\d{6}$"; //1hwa31aa5ae006086 example
    static final String LICENSE_NUMBER_PATTERN = "^[A-Z](?:\\d[- ]*){14}$";
    static final String EMAIL_PATTERN = "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";
    static final String PHONE_PATTERN = "^[0-9.()-]{10}";
}
