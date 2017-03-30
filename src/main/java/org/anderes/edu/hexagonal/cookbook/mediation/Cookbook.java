package org.anderes.edu.hexagonal.cookbook.mediation;

import org.anderes.edu.hexagonal.cookbook.core.MasterControlProgram;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.apache.commons.lang3.Validate;

public abstract class Cookbook {

    public static UserInterfaceRecipeService getUserInterfaceRecipeService() {
        return MasterControlProgram.getInstance().getUserInterfaceRecipeService();
    }
    
    public static void registerRepositoryPort(final RepositoryPort port) {
        Validate.notNull(port);
        MasterControlProgram.getInstance().registerRepositoryPort(port);
    }
}
