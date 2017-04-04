package org.anderes.edu.hexagonal.cookbook.core;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.mediation.CookbookException;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.anderes.edu.hexagonal.cookbook.mediation.CookbookException.ExceptionType.*;

public class RepositoryRecipeService {

    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private ValidatorService validatorService;
    private NotificationService notificationService;
    
    @Inject
    public RepositoryRecipeService(ValidatorService validatorService, NotificationService notificationService) {
        super();
        this.validatorService = validatorService;
        this.notificationService = notificationService;
    }

    public RecipeDomainObject findRecipeById(String id, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> recipeOptional = repositoryPort.findRecipeById(id);
        final RecipeDomainObject recipe = recipeOptional.orElseThrow(() -> new CookbookException(NO_RESULT));
        final Set<ConstraintViolation<RecipeDomainObject>> constraints = validatorService.validate(recipe);
        if (!constraints.isEmpty()) {
            logger.warn("Incorrect data from the repository!"); 
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
        recipe.setEditingDate(LocalDateTime.now());
        repositoryPort.updateRecipe(recipe);
        notificationService.eventUpdateRecipe(recipe);
    }

    public void addNewRecipe(final RecipeDomainObject recipe, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> findRecipe = repositoryPort.findRecipeById(recipe.getId());
        if (findRecipe.isPresent()) {
            logger.warn("The dataset can not be added because it already exists");
            throw new CookbookException(ILLEGAL_STATE);
        }
        recipe.setAddingDate(LocalDateTime.now());
        recipe.setEditingDate(LocalDateTime.now());
        repositoryPort.addNewRecipe(recipe);
        notificationService.eventNewRecipe(recipe);
    }
    
    public void bulkAddRecipe(final RecipeDomainObject recipe, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> findRecipe = repositoryPort.findRecipeById(recipe.getId());
        if (findRecipe.isPresent()) {
            logger.warn("The dataset can not be added because it already exists");
            throw new CookbookException(ILLEGAL_STATE);
        }
        repositoryPort.addNewRecipe(recipe);
        notificationService.eventNewRecipe(recipe);
    }
    
    public Map<String, String> getRecipeOverview(final RepositoryPort repositoryPort) {
        return repositoryPort.getRecipeOverview();
    }

    public void removeRecipe(final RecipeDomainObject recipe, final RepositoryPort repositoryPort) {
        final Optional<RecipeDomainObject> findRecipe = repositoryPort.findRecipeById(recipe.getId());
        if (findRecipe.isPresent()) {
            repositoryPort.removeRecipe(recipe);
            notificationService.eventRemoveRecipe(recipe);
        }
    }

    public Set<RecipeDomainObject> findRecipesByTags(final Set<String> tags, final RepositoryPort repositoryPort) {
        final Set<RecipeDomainObject> recipes = repositoryPort.findRecipesByTags(tags);
        Optional<RecipeDomainObject> notValidRecipe = recipes.stream().filter(recipe -> !validatorService.validate(recipe).isEmpty()).findAny();
        if (notValidRecipe.isPresent()) {
            logger.warn("Incorrect data from the repository!"); 
            throw new CookbookException(CONSTRAINT_VIOLATION);
        }
        return recipes;
    }
}
