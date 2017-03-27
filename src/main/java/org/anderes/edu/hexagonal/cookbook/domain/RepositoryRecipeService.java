package org.anderes.edu.hexagonal.cookbook.domain;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;

public class RepositoryRecipeService {

    private ValidatorService validatorService;
    
    @Inject
    public RepositoryRecipeService(ValidatorService validatorService) {
        super();
        this.validatorService = validatorService;
    }

    public RecipeDomainObject findRecipeById(String id, final RepositoryPort repositoryPort) {
        final RecipeDomainObject recipe = repositoryPort.findRecipeById(id);
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validatorService.validate(recipe);
        if (!constraints.isEmpty()) {
            throw new ConstraintViolationException(constraints);
        }
        return recipe;
    }

    public void updateRecipe(final RecipeDomainObject recipe, final RepositoryPort repositoryPort) {
        repositoryPort.updateRecipe(recipe);
    }

}
