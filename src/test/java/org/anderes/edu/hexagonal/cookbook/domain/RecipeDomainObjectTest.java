package org.anderes.edu.hexagonal.cookbook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import static java.time.Month.*;

import org.junit.Test;

public class RecipeDomainObjectTest {

    @Test
    public void shouldBeCorrectRating() {
        // given
        final RecipeDomainObject recipe = new RecipeDomainObject();
        // when
        recipe.setRating(0);
        // then
        assertThat(recipe.getRating().isPresent(), is(false));
    }
    
    @Test
    public void shouldBeCorrectTag() {
        // given
        final RecipeDomainObject recipe = getRecipeDomainObject();
        // then
        assertThat(recipe.addTag("pasta").getTags().size(), is(2));
        assertThat(recipe.addTag("vegan").getTags().size(), is(3));
    }
    
    @Test
    public void shouldBeCorrectIngredient() {
        // given
        final RecipeDomainObject recipe = getRecipeDomainObject();
        // then
        assertThat(recipe.addIngredient(getIngredient()).getIngredients().size(), is(1));
        assertThat(recipe.removeIngredient(getIngredient()).getIngredients().size(), is(0));
    }
    
    @Test
    public void shouldBeEquals() {
        // given
        final RecipeDomainObject recipe_1 = getRecipeDomainObject();
        final RecipeDomainObject recipe_2 = getRecipeDomainObject();
        //then
        assertNotSame(recipe_1, recipe_2);
        assertThat(recipe_1, is(recipe_2));
        assertEquals(recipe_1, recipe_2);
    }

    private RecipeDomainObject getRecipeDomainObject() {
        final RecipeDomainObject recipe = new RecipeDomainObject("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF");
        recipe.setTitle("Pasta mit Gemüse").setAddingDate(LocalDateTime.of(2018, JANUARY, 1, 13, 00)).setCookingTime(30)
            .setNoOfPeople("2").setPreparation("Es gibt nicht viel zu tun").setRating(4).setEditingDate(LocalDateTime.of(2018, JANUARY, 12, 16, 00))
            .setSetupTime(10).addTag("vegetarisch").addTag("pasta").addIngredient(getIngredient())
            .setNutritiveValue(getNutritiveValue());
        return recipe;
    }
    
    private IngredientDomainObject getIngredient() {
        return new IngredientDomainObject("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF", "2EL", "Öl", "Bio");
    }
    
    private NutritiveValueDomanObject getNutritiveValue() {
        return new NutritiveValueDomanObject("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF", 300, 200, 100, 50);
    }
}
