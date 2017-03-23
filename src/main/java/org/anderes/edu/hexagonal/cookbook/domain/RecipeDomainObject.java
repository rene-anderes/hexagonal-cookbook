package org.anderes.edu.hexagonal.cookbook.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecipeDomainObject {

    @NotNull
    private String id = UUID.randomUUID().toString();
    @NotNull
    @Size(min = 5, max = 255)
    private String title;
    @NotNull
    @Size(min = 5, max = 1000)
    private String preparation;
    private Optional<String> preamble = Optional.empty();
    @NotNull
    @Size(min = 1, max = 10)
    private String noOfPeople;
    private LocalDateTime editingDate = LocalDateTime.now();
    private LocalDateTime addingDate = LocalDateTime.now();
    private Optional<Integer> rating = Optional.empty();
    @Valid
    private Set<ImageDomainObject> images = new HashSet<>();
    @Valid
    @Size(min = 1)
    private Set<IngredientDomainObject> ingredients = new HashSet<>();
    private Set<String> tags = new HashSet<String>();
    @Valid /* nicht Optional, da Bean Validation 1.x nicht mit Optional umgehen kann */
    private NutritiveValueDomanObject nutritiveValue;

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
        return rating;
    }

    public RecipeDomainObject setRating(Integer rating) {
        this.rating = Optional.of(rating);
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
        images.add(image);
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
        return preamble;
    }

    public RecipeDomainObject setPreamble(final String preamble) {
        this.preamble = Optional.of(preamble);
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
}
