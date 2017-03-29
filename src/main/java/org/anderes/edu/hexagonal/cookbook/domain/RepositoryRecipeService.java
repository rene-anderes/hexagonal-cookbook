package org.anderes.edu.hexagonal.cookbook.domain;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.anderes.edu.hexagonal.cookbook.mediation.CookbookException;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.anderes.edu.hexagonal.cookbook.mediation.CookbookException.ExceptionType.*;

public class RepositoryRecipeService {

    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private ValidatorService validatorService;
    
    @Inject
    public RepositoryRecipeService(ValidatorService validatorService) {
        super();
        this.validatorService = validatorService;
    }

    public RecipeDomainObject findRecipeById(String id, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> recipeOptional = repositoryPort.findRecipeById(id);
        final RecipeDomainObject recipe = recipeOptional.orElseThrow(() -> new CookbookException(NO_RESULT));
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validatorService.validate(recipe);
        if (!constraints.isEmpty()) {
            logger.warn("Incorrect data from the database!"); 
            throw new CookbookException(new ConstraintViolationException(constraints));
        }
        return recipe;
    }

    public void updateRecipe(final RecipeDomainObject recipe, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> findRecipe = repositoryPort.findRecipeById(recipe.getId());
        if (!findRecipe.isPresent()) {
            logger.warn("Update can not be performed because the record does not exist!"); 
            throw new CookbookException(ILLEGAL_STATE);
        }
        repositoryPort.updateRecipe(recipe);
    }

    public void addNewRecipe(final RecipeDomainObject recipe, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> findRecipe = repositoryPort.findRecipeById(recipe.getId());
        if (findRecipe.isPresent()) {
            logger.warn("The dataset can not be added because it already exists");
            throw new CookbookException(ILLEGAL_STATE);
        }
        repositoryPort.addNewRecipe(recipe);
    }

    public Map<String, String> getRecipeOverview(final RepositoryPort repositoryPort) {
        return repositoryPort.getRecipeOverview();
    }

}
