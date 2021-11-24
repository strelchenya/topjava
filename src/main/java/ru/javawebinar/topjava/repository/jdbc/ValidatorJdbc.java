package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
public class ValidatorJdbc {
    private ValidatorFactory validatorFactory;
    private Validator validator;

    public ValidatorJdbc() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.usingContext().getValidator();
    }

    public <T> boolean validation(T t){
        return validator.validate(t).size() > 0;
    }
}
