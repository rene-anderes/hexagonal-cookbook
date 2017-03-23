package org.anderes.edu.hexagonal.cookbook.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * Zutat
 */
public class IngredientDomainObject {

    @NotNull
    @Size(min = 1, max = 25)
    private String portion;
    @NotNull
    @Size(min = 1, max = 255)
    private String description;
    @NotNull
    @Size(min = 1, max = 255)
    private String comment;

    public IngredientDomainObject() {
    }

    public IngredientDomainObject(final String portion, final String description, final String comment) {
        super();
        this.portion = portion;
        this.description = description;
        this.comment = comment;
    }

    public String getPortion() {
        return portion;
    }

    public IngredientDomainObject setPortion(final String quantity) {
        this.portion = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IngredientDomainObject setDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public IngredientDomainObject setComment(final String comment) {
        this.comment = comment;
        return this;
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
        return new EqualsBuilder().append(portion, rhs.portion).append(description, rhs.description).append(comment, rhs.comment).isEquals();
    }

    @Override
    public String toString() {
        return new StrBuilder().append(portion).append(description).append(comment).build();
    }
}
