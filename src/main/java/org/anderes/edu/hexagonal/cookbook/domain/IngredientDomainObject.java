package org.anderes.edu.hexagonal.cookbook.domain;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * Zutat
 */
public class IngredientDomainObject implements Serializable {

    /* Klassenvariablen nicht Optional, da Bean Validation 1.x nicht mit Optional umgehen kann */
    
    private static final long serialVersionUID = 1L;
    @Size(min = 1, max = 25)
    private String portion;
    @NotNull
    @Size(min = 1, max = 255)
    private String description;
    @Size(min = 1, max = 255)
    private String comment;

    IngredientDomainObject() {
    
    }

    public IngredientDomainObject(final String portion, final String description, final String comment) {
        this(portion, description);
        Validate.notNull(comment);
        this.comment = comment;
    }
    
    public IngredientDomainObject(final String portion, final String description) {
        this(description);
        Validate.notNull(portion);
        this.portion = portion;
    }
    
    public IngredientDomainObject(final String description) {
        super();
        Validate.notNull(description);
        this.description = description;
    }

    public String getPortion() {
        return portion;
    }

    public String getDescription() {
        return description;
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(portion).append(description).append(comment).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        IngredientDomainObject rhs = (IngredientDomainObject) obj;
        return new EqualsBuilder().append(portion, rhs.portion)
                        .append(description, rhs.description).append(comment, rhs.comment)
                        .isEquals();
    }

    @Override
    public String toString() {
        return new StrBuilder().append(portion).append(description).append(comment).build();
    }
}
