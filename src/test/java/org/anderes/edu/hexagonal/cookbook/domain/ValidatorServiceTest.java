package org.anderes.edu.hexagonal.cookbook.domain;

import static java.time.Month.MARCH;
import static org.anderes.edu.hexagonal.cookbook.domain.RecipeBuilder.createRecipe;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;

public class ValidatorServiceTest {

    private ValidatorService validator;

    @Before
    public void setup() {
        validator = new ValidatorService();
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
        assertThat(constraints.size(), is(3));
        final Iterator<ConstraintViolation<RecipeDomainObject>> iterator = constraints.iterator();
//        final ConstraintViolation<RecipeDomainObject> constraintViolation_1 = iterator.next();
//        assertThat(constraintViolation_1.getInvalidValue(), is("falsche-ID"));
//        assertThat(constraintViolation_1.getMessageTemplate(), is("{javax.validation.constraints.Pattern.message}"));
//        assertThat(constraintViolation_1.getPropertyPath().toString(), is("id"));
        final ConstraintViolation<RecipeDomainObject> constraintViolation_4 = iterator.next();
        assertThat(constraintViolation_4.getInvalidValue(), is("falsche-ID"));
        assertThat(constraintViolation_4.getMessageTemplate(), is("{javax.validation.constraints.Size.message}"));
        assertThat(constraintViolation_4.getPropertyPath().toString(), is("id"));
        final ConstraintViolation<RecipeDomainObject> constraintViolation_2 = iterator.next();
        assertThat(constraintViolation_2.getInvalidValue(), is(nullValue()));
        assertThat(constraintViolation_2.getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(constraintViolation_2.getPropertyPath().toString(), is("preparation"));
        final ConstraintViolation<RecipeDomainObject> constraintViolation_3 = iterator.next();
        assertThat(constraintViolation_3.getInvalidValue(), is(nullValue()));
        assertThat(constraintViolation_3.getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(constraintViolation_3.getPropertyPath().toString(), is("noOfPeople"));
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
