package org.anderes.edu.hexagonal.cookbook.port;

import java.util.Map;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public interface UserInterfacePort {

    void showRecipe(RecipeDomainObject recipe);
    
    /**
     * Wird aufgerufen um eine Liste von ID's / Rezepttitel anzuzeigen
     * 
     * @param overview Map mit Key = ID, Value = Rezepttitel
     */
    void showRecipeOverview(Map<String, String> overview);

}
