package org.anderes.edu.hexagonal.cookbook.mediation;

import org.anderes.edu.hexagonal.cookbook.domain.MasterControlProgramm;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;

public abstract class Cookbook {

    public static UserInterfaceRecipeService getUserInterfaceRecipeService() {
        return MasterControlProgramm.getInstance().getUserInterfaceRecipeService();
    }
    
    public static void registerRepositoryPort(final RepositoryPort port) {
        MasterControlProgramm.getInstance().registerRepositoryPort(port);
    }
}
