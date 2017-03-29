package org.anderes.edu.hexagonal.cookbook.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public class RecipeDomainObjectValidator implements
        ConstraintValidator<ValidRecipeDomainObject, RecipeDomainObject> {

    @Override
    public void initialize(ValidRecipeDomainObject constraintAnnotation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isValid(RecipeDomainObject value, ConstraintValidatorContext context) {
        return true;
    }


}
