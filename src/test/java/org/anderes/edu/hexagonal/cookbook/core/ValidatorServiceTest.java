package org.anderes.edu.hexagonal.cookbook.core;

import static java.time.Month.MARCH;
import static org.anderes.edu.hexagonal.cookbook.core.RecipeBuilder.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.anderes.edu.hexagonal.cookbook.core.ValidatorService;
import org.anderes.edu.hexagonal.cookbook.domain.IngredientDomainObject;
import org.anderes.edu.hexagonal.cookbook.domain.NutritiveValueDomanObject;
import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.junit.Before;
import org.junit.Test;

public class ValidatorServiceTest {

    private ValidatorService validator;

    @Before
    public void setup() {
        validator = new ValidatorService();
    }

    @Test
    public void shouldBeCorrectRandomRecipeDomainObject() {
        // given
        final RecipeDomainObject recipe = createRandomRecipe();
        // when
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validator.validate(recipe);
        // then
        assertThat(constraints.size(), is(0));
    }
    
    @Test
    public void shouldBeCorrectRecipeDomainObject() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        // when
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validator.validate(recipe);
        // then
        assertThat(constraints.size(), is(0));
    }
    
    @Test
    public void shouldBeNotValidRecipeDomainObject() {
        // given
        final RecipeDomainObject recipe = createNotValidRecipe();
        // when
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validator.validate(recipe);
        // then
        assertThat(constraints.size(), is(4));
        Optional<ConstraintViolation<RecipeDomainObject>> find1 = 
                        constraints.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("id"))
                            .filter(c -> c.getInvalidValue().equals("falsche-ID"))
                            .filter(c -> c.getMessageTemplate().equals("{javax.validation.constraints.Pattern.message}"))
                            .findAny();
        assertThat(find1.isPresent(), is(true));
        Optional<ConstraintViolation<RecipeDomainObject>> find2 = 
                        constraints.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("id"))
                            .filter(c -> c.getInvalidValue().equals("falsche-ID"))
                            .filter(c -> c.getMessageTemplate().equals("{javax.validation.constraints.Size.message}"))
                            .findAny();
        assertThat(find2.isPresent(), is(true));
        Optional<ConstraintViolation<RecipeDomainObject>> find3 = 
                        constraints.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("noOfPeople"))
                            .filter(c -> c.getInvalidValue() == null)
                            .filter(c -> c.getMessageTemplate().equals("{javax.validation.constraints.NotNull.message}"))
                            .findAny();
        assertThat(find3.isPresent(), is(true));
        Optional<ConstraintViolation<RecipeDomainObject>> find4 = 
                        constraints.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("preparation"))
                            .filter(c -> c.getInvalidValue() == null)
                            .filter(c -> c.getMessageTemplate().equals("{javax.validation.constraints.NotNull.message}"))
                            .findAny();
        assertThat(find4.isPresent(), is(true));
    }

    private RecipeDomainObject createNotValidRecipe() {
        final RecipeDomainObject recipe = new RecipeDomainObject("falsche-ID");
        recipe.setTitle("Curry-Spinat mit Mandel-Kartoffeln")
            .setNutritiveValue(new NutritiveValueDomanObject(572, 18, 37, 35))
            .setRating(4)
            .setAddingDate(LocalDateTime.of(2017, MARCH, 27, 16, 00))
            .setEditingDate(LocalDateTime.of(2017, MARCH, 27, 16, 32))
            .addIngredient(new IngredientDomainObject("400 g", "Kartoffeln", "klein festkochend"))
            .addIngredient(new IngredientDomainObject("500 g", "Blattspinat frisch", "siehe Rezept-Tipp"));
        return recipe;
    }
}
