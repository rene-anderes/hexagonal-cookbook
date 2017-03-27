package org.anderes.edu.hexagonal.cookbook.domain;

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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.Validate;
/**
 * Rezept
 */
public class RecipeDomainObject {

    /* Klassenvariablen nicht Optional, da Bean Validation 1.x nicht mit Optional umgehen kann */
    
    @NotNull @Size(min = 36, max = 36) @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
    @Valid
    private Set<ImageDomainObject> images = new HashSet<>();
    @Valid @Size(min = 1)
    private Set<IngredientDomainObject> ingredients = new HashSet<>();
    private Set<String> tags = new HashSet<String>();
    @Valid
    private NutritiveValueDomanObject nutritiveValue;
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
        this.rating = rating;
        return this;
    }

    public RecipeDomainObject setEditingDate(LocalDateTime editingDate) {
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
        this.id = id;
        return this;
    }
    
    public String getTitle() {
        return title;
    }

    public RecipeDomainObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public RecipeDomainObject addIngredient(final IngredientDomainObject ingredient) {
        ingredients.add(ingredient);
        return this;
    }
    
    public void removeIngredient(final IngredientDomainObject ingredient) {
        ingredients.remove(ingredient);
    }

    public Set<IngredientDomainObject> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeDomainObject setPreparation(final String preparation) {
        this.preparation = preparation;
        return this;
    }

    public Optional<String> getPreamble() {
        return Optional.ofNullable(preamble);
    }

    public RecipeDomainObject setPreamble(final String preamble) {
        this.preamble = preamble;
        return this;
    }

    public String getNoOfPeople() {
        return noOfPeople;
    }

    public RecipeDomainObject setNoOfPeople(final String noOfPeople) {
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
        return Optional.ofNullable(nutritiveValue);
    }
    
    public RecipeDomainObject setNutritiveValue(NutritiveValueDomanObject nutritiveValue) {
        this.nutritiveValue = nutritiveValue;
        return this;
    }
    
    public RecipeDomainObject removeNutritiveValue() {
        this.nutritiveValue = null;
        return this;
    }

    public RecipeDomainObject setHint(String hint) {
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
        this.setupTime = setupTime;
        return this;
    }

    public Optional<Integer>  getCookingTime() {
        if (cookingTime == 0) {
            return Optional.empty();
        }
        return Optional.of(cookingTime);
    }

    public RecipeDomainObject setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }
}
