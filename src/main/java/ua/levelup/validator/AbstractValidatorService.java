package ua.levelup.validator;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class AbstractValidatorService<T> {

    @Autowired
    protected Validator validator;

    public Set<ConstraintViolation<T>> validate(T object){
        return validator.validate(object);
    }
}