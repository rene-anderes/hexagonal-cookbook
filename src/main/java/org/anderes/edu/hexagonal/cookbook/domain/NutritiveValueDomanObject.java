package org.anderes.edu.hexagonal.cookbook.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.anderes.edu.hexagonal.cookbook.core.validator.ValidUUID;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Nährwert
 */
public class NutritiveValueDomanObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull @ValidUUID
    private String id = UUID.randomUUID().toString();
    @NotNull @Min(1)
    private Integer calorie;
    @NotNull @Min(1)
    private Integer protein;
    @NotNull @Min(1)
    private Integer fat;
    @NotNull @Min(1)
    private Integer carb;
    
    private NutritiveValueDomanObject() {
        super();
    }
    
    public NutritiveValueDomanObject(final String id, Integer calorie, Integer protein, Integer fat, Integer carb) {
        this(calorie, protein, fat, carb);
        Validate.notNull(id);
        this.id = id;
    }
    
    public NutritiveValueDomanObject(Integer calorie, Integer protein, Integer fat, Integer carb) {
        this();
        this.calorie = calorie;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
    }

    public Integer getCalorie() {
        return calorie;
    }

    public void setCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getCarb() {
        return carb;
    }

    public void setCarb(Integer carb) {
        this.carb = carb;
    }
    
    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(calorie).append(protein).append(fat).append(carb).toHashCode();
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
        NutritiveValueDomanObject rhs = (NutritiveValueDomanObject) obj;
        return new EqualsBuilder().append(id, rhs.id).append(calorie, rhs.calorie)
                        .append(protein, rhs.protein).append(fat, rhs.fat).append(carb, rhs.carb)
                        .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).append(calorie).append(protein).append(fat).append(carb).build();
    }
}
