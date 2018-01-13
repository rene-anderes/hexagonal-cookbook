package org.anderes.edu.hexagonal.cookbook.core.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Constraint(validatedBy = {})
@Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
@ReportAsSingleViolation
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {

    String message() default "{org.anderes.validation.uuid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
