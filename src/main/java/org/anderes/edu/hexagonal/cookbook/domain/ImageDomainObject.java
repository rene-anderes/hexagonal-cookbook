package org.anderes.edu.hexagonal.cookbook.domain;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.anderes.edu.hexagonal.cookbook.core.validator.ValidUUID;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Bild eines Rezepts
 */
public class ImageDomainObject implements Serializable {

    /* Klassenvariablen nicht Optional, da Bean Validation 1.x nicht mit Optional umgehen kann */
    
    private static final long serialVersionUID = 1L;
    @NotNull @ValidUUID
    private String id = UUID.randomUUID().toString();
    @NotNull
    @Size(min = 5, max = 255)
    private String url;
    @Size(min = 1, max = 255)
    private String description;

    private ImageDomainObject() {
        super();
    }
    
    public ImageDomainObject(String url) {
        this();
        Validate.notNull(url);
        this.url = url;
    }

    public ImageDomainObject(String url, String description) {
        this(url);
        Validate.notNull(description);
        this.description = description;
    }
    
    public ImageDomainObject(String id, String url, String description) {
        this(url, description);
        Validate.notNull(id);
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public String toString() {
        return String.format("Image: [id='%s'], [url='%s'], [description='%s']", id, url, description);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(url).append(description).toHashCode();

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
        return new EqualsBuilder().append(id, rhs.id).append(url, rhs.url).append(description, rhs.description).isEquals();
    }
}
