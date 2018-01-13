package org.anderes.edu.hexagonal.cookbook.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.anderes.edu.hexagonal.cookbook.core.validator.ValidUUID;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
/**
 * Rezept
 */
public class RecipeDomainObject implements Serializable {

    /* Klassenvariablen nicht Optional, da Bean Validation 1.x nicht mit Optional umgehen kann */

    private static final long serialVersionUID = 1L;
    @NotNull @ValidUUID
    private String id = UUID.randomUUID().toString();
    @NotNull @Size(min = 5, max = 255)
    private String title;
    @NotNull @Size(min = 5, max = 10000)
    private String preparation;
    @Size(min = 5, max = 5000)
    private String preamble;
    @NotNull @Size(min = 1, max = 10)
    private String noOfPeople;
    private LocalDateTime editingDate = LocalDateTime.now();
    private LocalDateTime addingDate = LocalDateTime.now();
    @NotNull @Min(1) @Max(5)
    private Integer rating = Integer.valueOf(0);
    @NotNull @Valid
    private Set<ImageDomainObject> images = new HashSet<>();
    @NotNull @Valid @Size(min = 1)
    private Set<IngredientDomainObject> ingredients = new HashSet<>();
    @NotNull
    private Set<String> tags = new HashSet<String>();
    @Valid
    private NutritiveValueDomanObject nutritiveValueObject;
    @Size(min = 5, max = 5000)
    private String hint;
    @NotNull @Min(0) @Max(480)
    private Integer setupTime = Integer.valueOf(0);
    @NotNull @Min(0) @Max(480)
    private Integer cookingTime = Integer.valueOf(0);

    public RecipeDomainObject() {
        super();
    }
    
    public RecipeDomainObject(String id) {
        this();
        this.id = id;
    }

    public LocalDateTime getEditingDate() {
        return editingDate;
    }

    public LocalDateTime getAddingDate() {
        return addingDate;
    }

    public RecipeDomainObject setAddingDate(LocalDateTime addingDate) {
        Validate.notNull(addingDate);
        this.addingDate = addingDate;
        return this;
    }

    public Optional<Integer> getRating() {
        if (rating == 0) {
            return Optional.empty();
        }
        return Optional.of(rating);
    }

    public RecipeDomainObject setRating(Integer rating) {
        Validate.notNull(rating);
        this.rating = rating;
        return this;
    }

    public RecipeDomainObject setEditingDate(LocalDateTime editingDate) {
        Validate.notNull(editingDate);
        this.editingDate = editingDate;
        return this;
    }

    public Set<ImageDomainObject> getImages() {
        return Collections.unmodifiableSet(images);
    }

    public RecipeDomainObject addImage(ImageDomainObject image) {
        Validate.notNull(image);
        images.add(image);
        return this;
    }
    
    public RecipeDomainObject removeImage(ImageDomainObject image) {
        Validate.notNull(image);
        images.remove(image);
        return this;
    }

    public String getId() {
        return id;
    }

    public RecipeDomainObject setId(String id) {
        Validate.notNull(id);
        this.id = id;
        return this;
    }
    
    public String getTitle() {
        return title;
    }

    public RecipeDomainObject setTitle(String title) {
        Validate.notNull(title);
        this.title = title;
        return this;
    }

    public RecipeDomainObject addIngredient(final IngredientDomainObject ingredient) {
        Validate.notNull(ingredient);
        ingredients.add(ingredient);
        return this;
    }
    
    public RecipeDomainObject removeIngredient(final IngredientDomainObject ingredient) {
        Validate.notNull(ingredient);
        ingredients.remove(ingredient);
        return this;
    }

    public Set<IngredientDomainObject> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeDomainObject setPreparation(final String preparation) {
        Validate.notNull(preparation);
        this.preparation = preparation;
        return this;
    }

    public Optional<String> getPreamble() {
        return Optional.ofNullable(preamble);
    }

    public RecipeDomainObject setPreamble(final String preamble) {
        Validate.notNull(preamble);
        this.preamble = preamble;
        return this;
    }
    
    public RecipeDomainObject removePreamble() {
        preamble = null;
        return this;
    }

    public String getNoOfPeople() {
        return noOfPeople;
    }

    public RecipeDomainObject setNoOfPeople(final String noOfPeople) {
        Validate.notNull(noOfPeople);
        this.noOfPeople = noOfPeople;
        return this;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public RecipeDomainObject addTag(final String tag) {
        Validate.notNull(tag);
        this.tags.add(tag);
        return this;
    }
    
    public RecipeDomainObject removeTag(final String tag) {
        this.tags.remove(tag);
        return this;
    }

    public Optional<NutritiveValueDomanObject> getNutritiveValue() {
        return Optional.ofNullable(nutritiveValueObject);
    }
    
    public RecipeDomainObject setNutritiveValue(NutritiveValueDomanObject nutritiveValue) {
        Validate.notNull(nutritiveValue);
        this.nutritiveValueObject = nutritiveValue;
        return this;
    }
    
    public RecipeDomainObject removeNutritiveValue() {
        this.nutritiveValueObject = null;
        return this;
    }

    public RecipeDomainObject setHint(String hint) {
        Validate.notNull(hint);
        this.hint = hint;
        return this;
    }
    
    public Optional<String> getHint() {
        return Optional.ofNullable(hint);
    }

    public Optional<Integer> getSetupTime() {
        if (setupTime == 0) {
            return Optional.empty();
        }
        return Optional.of(setupTime);
    }

    public RecipeDomainObject setSetupTime(Integer setupTime) {
        Validate.notNull(setupTime);
        this.setupTime = setupTime;
        return this;
    }

    public Optional<Integer> getCookingTime() {
        if (cookingTime == 0) {
            return Optional.empty();
        }
        return Optional.of(cookingTime);
    }

    public RecipeDomainObject setCookingTime(Integer cookingTime) {
        Validate.notNull(cookingTime);
        this.cookingTime = cookingTime;
        return this;
    }
    

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(title).append(preparation)
                        .append(preamble).append(noOfPeople).append(editingDate)
                        .append(addingDate).append(rating).append(images)
                        .append(ingredients).append(tags).append(nutritiveValueObject)
                        .append(hint).append(setupTime).append(cookingTime)
                        .toHashCode();
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
        RecipeDomainObject rhs = (RecipeDomainObject) obj;
        return new EqualsBuilder().append(id, rhs.id).append(title, rhs.title).append(preparation, rhs.preparation)
                        .append(preamble, rhs.preamble).append(noOfPeople, rhs.noOfPeople).append(editingDate, rhs.editingDate)
                        .append(addingDate, rhs.addingDate).append(rating, rhs.rating).append(images, rhs.images)
                        .append(ingredients, rhs.ingredients).append(tags, rhs.tags)
                        .append(nutritiveValueObject, rhs.nutritiveValueObject)
                        .append(hint, rhs.hint).append(setupTime, rhs.setupTime).append(cookingTime, rhs.cookingTime)
                        .isEquals();
    }
}
