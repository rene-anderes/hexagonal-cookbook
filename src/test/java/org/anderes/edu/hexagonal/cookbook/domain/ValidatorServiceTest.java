package org.anderes.edu.hexagonal.cookbook.domain;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
        final RecipeDomainObject recipe = createNewRecipe();
        // when
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validator.validate(recipe);
        // then
        assertThat(constraints.size(), is(0));
    }
    
    private RecipeDomainObject createNewRecipe() {
        final RecipeDomainObject recipe = new RecipeDomainObject();
        recipe.setTitle("Arabische Spaghetti").setNoOfPeople("4")
                        .setPreparation("Wasser aufsetzen, salzen und Spaghetti al dente kochen.")
                        .addIngredient(createRandomIngredient());
        return recipe;
    }

    private IngredientDomainObject createRandomIngredient() {
        return new IngredientDomainObject(randomAlphabetic(5), randomAlphabetic(5), randomAlphabetic(5));
    }
}
