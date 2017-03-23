package org.anderes.edu.hexagonal.cookbook.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = { RecipeDomainObjectValidator.class })
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRecipeDomainObject {
    String message() default "{ch.edu.validation.recipe}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
