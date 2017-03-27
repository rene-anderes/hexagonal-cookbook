package org.anderes.edu.hexagonal.cookbook.mediation;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.domain.RepositoryRecipeService;
import org.anderes.edu.hexagonal.cookbook.domain.ValidatorService;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.anderes.edu.hexagonal.cookbook.port.UserInterfacePort;

public class UserInterfaceRecipeService {

    private RepositoryRecipeService repositoryService;
    private ValidatorService validatorService;

    @Inject
    public UserInterfaceRecipeService(RepositoryRecipeService repositoryService, ValidatorService validatorService) {
        this.repositoryService = repositoryService;
        this.validatorService = validatorService;
    }

    public void getRecipeById(String id, final UserInterfacePort userInterfacePort) {
        final RepositoryPort repositoryPort = MasterControlProgramm.getInstance().getRepositoryPort();
        final RecipeDomainObject recipe = repositoryService.findRecipeById(id, repositoryPort);
        userInterfacePort.showRecipe(recipe);
    }
    
    public void setRecipe(final RecipeDomainObject recipe) {
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validatorService.validate(recipe);
        if (!constraints.isEmpty()) {
            throw new ConstraintViolationException(constraints);
        }
        final RepositoryPort repositoryPort = MasterControlProgramm.getInstance().getRepositoryPort();
        repositoryService.updateRecipe(recipe, repositoryPort);
    }

}
