package ua.levelup.validator;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @param <T>
 */
public class AbstractValidatorService<T> {

    @Autowired
    protected Validator validator;

    public Set<ConstraintViolation<T>> validate(@NonNull T object){
        return validator.validate(object);
    }
}