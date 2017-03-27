package org.anderes.edu.hexagonal.cookbook.port;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public interface UserInterfacePort {

    void showRecipe(RecipeDomainObject recipe);

}
