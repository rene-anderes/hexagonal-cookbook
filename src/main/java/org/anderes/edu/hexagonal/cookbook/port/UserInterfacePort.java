package org.anderes.edu.hexagonal.cookbook.port;

import java.util.Map;
import java.util.Set;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public interface UserInterfacePort {

    /**
     * Wird aufgerufen um ein einzelnes Rezept anzuzeigen
     * 
     * @param recipe
     */
    void showRecipe(RecipeDomainObject recipe);

    /**
     * Wird aufgerufen um eine Liste von ID's / Rezepttitel anzuzeigen
     * 
     * @param overview
     *            Map mit Key = ID, Value = Rezepttitel
     */
    void showRecipeOverview(Map<String, String> overview);

    /**
     * Wird aufgerufen um eine Liste von Rezepten anzuzeigen.
     * 
     * @param recipeSet
     *            Liste mit Rezepten
     */
    void showRecipes(Set<RecipeDomainObject> recipeSet);

}
