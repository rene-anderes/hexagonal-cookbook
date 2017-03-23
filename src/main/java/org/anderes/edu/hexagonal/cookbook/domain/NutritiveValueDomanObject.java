package org.anderes.edu.hexagonal.cookbook.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Nährwert
 */
public class NutritiveValueDomanObject {

    @NotNull
    @Min(1)
    private Integer calorie;
    @NotNull
    @Min(1)
    private Integer protein;
    @NotNull
    @Min(1)
    private Integer fat;
    @NotNull
    @Min(1)
    private Integer carb;
    
    public NutritiveValueDomanObject(Integer calorie, Integer protein, Integer fat, Integer carb) {
        super();
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
}
