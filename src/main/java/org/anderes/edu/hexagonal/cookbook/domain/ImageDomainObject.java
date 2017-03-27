package org.anderes.edu.hexagonal.cookbook.domain;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Bild eines Rezepts
 */
public class ImageDomainObject {

    /* Klassenvariablen nicht Optional, da Bean Validation 1.x nicht mit Optional umgehen kann */
    
    @NotNull
    @Size(min = 5, max = 255)
    private String url;
    @Size(min = 1, max = 255)
    private String description;

    @SuppressWarnings("unused")
    private ImageDomainObject() {
        super();
    }

    public ImageDomainObject(String url, String description) {
        this(url);
        this.description = description;
    }
    
    public ImageDomainObject(String url) {
        super();
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public String toString() {
        return String.format("Image: [url='%s'], [description='%s']", url, description);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(url).append(description).toHashCode();

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ImageDomainObject rhs = (ImageDomainObject) obj;
        return new EqualsBuilder().append(url, rhs.url).append(description, rhs.description).isEquals();
    }
}
