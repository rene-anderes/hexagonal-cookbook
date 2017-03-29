package org.anderes.edu.hexagonal.cookbook.port;

import java.util.Map;
import java.util.Optional;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public interface RepositoryPort {

    Optional<RecipeDomainObject> findRecipeById(String id);

    void updateRecipe(RecipeDomainObject recipe);
    
    void addNewRecipe(RecipeDomainObject recipe);
    
    void removeRecipe(RecipeDomainObject recipe);

    Map<String, String> getRecipeOverview();

    String getVersion();

}
