package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
public class ValidationJdbcRepository {
   private ValidatorFactory validatorFactory;
   protected Validator validator;

    public ValidationJdbcRepository() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
}
