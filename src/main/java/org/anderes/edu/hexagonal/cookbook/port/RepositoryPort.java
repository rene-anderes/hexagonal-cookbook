package org.anderes.edu.hexagonal.cookbook.port;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public interface RepositoryPort {

    RecipeDomainObject findRecipeById(String id);

    void updateRecipe(RecipeDomainObject recipe);

}
