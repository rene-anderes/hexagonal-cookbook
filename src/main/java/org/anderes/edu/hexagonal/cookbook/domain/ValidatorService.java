package org.anderes.edu.hexagonal.cookbook.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.Validate;

public class ValidatorService {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    public Set<ConstraintViolation<RecipeDomainObject>> validate(final RecipeDomainObject recipe) {
        Validate.notNull(recipe, "Der Parameter darf nicht null sein");
        return validator.validate(recipe);
    }

}
