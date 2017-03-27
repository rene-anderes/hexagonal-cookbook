package org.anderes.edu.hexagonal.cookbook.domain;

import org.anderes.edu.hexagonal.cookbook.mediation.UserInterfaceRecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookbookConfig {

    @Bean
    public RepositoryRecipeService getRepositoryService() {
        return new RepositoryRecipeService(getValidatorServeice());
    }
    
    @Bean
    public ValidatorService getValidatorServeice() {
        return new ValidatorService();
    }
    
    @Bean
    public UserInterfaceRecipeService getUserInterfaceRecipeService() {
        return new UserInterfaceRecipeService(getRepositoryService(), getValidatorServeice());
    }
}
