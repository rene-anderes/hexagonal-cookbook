package org.anderes.edu.hexagonal.cookbook.core;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.mediation.CookbookException;
import org.anderes.edu.hexagonal.cookbook.mediation.UserInterfaceRecipeService;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.anderes.edu.hexagonal.cookbook.port.UserInterfacePort;

public class UserInterfaceRecipeServiceImpl implements UserInterfaceRecipeService {

    private RepositoryRecipeService repositoryService;
    private ValidatorService validatorService;

    @Inject
    public UserInterfaceRecipeServiceImpl(RepositoryRecipeService repositoryService, ValidatorService validatorService) {
        this.repositoryService = repositoryService;
        this.validatorService = validatorService;
    }

    @Override
    public void getRecipeById(String id, final UserInterfacePort userInterfacePort) {
        final RepositoryPort repositoryPort = MasterControlProgram.getInstance().getRepositoryPort();
        final RecipeDomainObject recipe = repositoryService.findRecipeById(id, repositoryPort);
        userInterfacePort.showRecipe(recipe);
    }
    
    @Override
    public void setRecipe(final RecipeDomainObject recipe) {
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validatorService.validate(recipe);
        if (!constraints.isEmpty()) {
            throw new CookbookException(new ConstraintViolationException(constraints));
        }
        final RepositoryPort repositoryPort = MasterControlProgram.getInstance().getRepositoryPort();
        repositoryService.updateRecipe(recipe, repositoryPort);
    }

    @Override
    public void addRecipe(final RecipeDomainObject recipe) {
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validatorService.validate(recipe);
        if (!constraints.isEmpty()) {
            throw new CookbookException(new ConstraintViolationException(constraints));
        }
        final RepositoryPort repositoryPort = MasterControlProgram.getInstance().getRepositoryPort();
        repositoryService.addNewRecipe(recipe, repositoryPort);
    }

    @Override
    public void getRecipeOverview(final UserInterfacePort userInterfacePort) {
        final RepositoryPort repositoryPort = MasterControlProgram.getInstance().getRepositoryPort();
        final Map<String, String> values = repositoryService.getRecipeOverview(repositoryPort);
        userInterfacePort.showRecipeOverview(values);
    }

}
