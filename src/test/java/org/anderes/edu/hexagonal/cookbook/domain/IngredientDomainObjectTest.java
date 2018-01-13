package org.anderes.edu.hexagonal.cookbook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IngredientDomainObjectTest {

    @Test
    public void shouldBeCorrectConstructor() {
        final IngredientDomainObject ingredient = createIngredient();
        
        assertThat(ingredient.getId(), is("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF"));
        assertThat(ingredient.getPortion(), is("2EL"));
        assertThat(ingredient.getDescription(), is("Öl"));
        assertThat(ingredient.getComment().isPresent(), is(true));
        assertThat(ingredient.getComment().get(), is("Bio"));
        
    }

    @Test
    public void shouldBeEquals() {
        final IngredientDomainObject ingredient_1 = createIngredient();
        final IngredientDomainObject ingredient_2 = createIngredient();
        
        assertNotSame(ingredient_1, ingredient_2);
        assertThat(ingredient_1, is(ingredient_2));
    }
    
    private IngredientDomainObject createIngredient() {
        return new IngredientDomainObject("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF", "2EL", "Öl", "Bio");
    }
}
