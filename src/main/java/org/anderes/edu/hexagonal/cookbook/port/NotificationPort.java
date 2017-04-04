package org.anderes.edu.hexagonal.cookbook.port;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

public interface NotificationPort {

    void eventAddNewRecipe(RecipeDomainObject recipe);

    void eventUpdateRecipe(RecipeDomainObject recipe);

    void eventRemoveRecipe(RecipeDomainObject recipe);

}
