package org.anderes.edu.hexagonal.cookbook.mediation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.anderes.edu.hexagonal.cookbook.mediation.CookbookException.ExceptionType.*;

/**
 * Diese Exception wird dazu benutzt, Probleme oder Fehler die durch den Benutzer
 * des Cookbook-Frameworks ausgelöst wurden, mitzuteilen.
 */
public final class CookbookException extends RuntimeException {
    
    public enum ExceptionType { 
        CONSTRAINT_VIOLATION("constraint violation exception"), NO_RESULT("no result exception"), UNKNOWN("unknown exception"),
        ILLEGAL_STATE("illegal state exception");
        
        private String message;

        ExceptionType(final String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    };
    
    private ExceptionType exceptionType = UNKNOWN;
    
    private static final long serialVersionUID = 1L;
    private Set<ConstraintViolation<?>> constraints = new HashSet<>();

    public CookbookException(final ExceptionType exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public CookbookException(final ConstraintViolationException e) {
        super(e);
        constraints = e.getConstraintViolations();
        exceptionType = CONSTRAINT_VIOLATION;
    }

    public Set<ConstraintViolation<?>> getConstraintViolations(){
        return Collections.unmodifiableSet(constraints);
    }
    
    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    @Override
    public String getMessage() {
        return exceptionType.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return exceptionType.getMessage() + " | " + super.getMessage();
    }

    @Override
    public String toString() {
        return exceptionType.getMessage();
    }
    
    
}
