package org.anderes.edu.hexagonal.cookbook.core;

import java.util.Set;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.port.NotificationPort;
import org.apache.commons.lang3.Validate;

public class NotificationService {

    public void eventNewRecipe(final RecipeDomainObject recipe) {
        Validate.notNull(recipe);
        final Set<NotificationPort> ports = MasterControlProgram.getInstance().getNotificationPorts();
        ports.stream().forEach(p -> p.eventAddNewRecipe(recipe));
    }

    public void eventUpdateRecipe(final RecipeDomainObject recipe) {
        Validate.notNull(recipe);
        final Set<NotificationPort> ports = MasterControlProgram.getInstance().getNotificationPorts();
        ports.stream().forEach(p -> p.eventUpdateRecipe(recipe));
    }

    public void eventRemoveRecipe(final RecipeDomainObject recipe) {
        Validate.notNull(recipe);
        final Set<NotificationPort> ports = MasterControlProgram.getInstance().getNotificationPorts();
        ports.stream().forEach(p -> p.eventRemoveRecipe(recipe));
    }

}
