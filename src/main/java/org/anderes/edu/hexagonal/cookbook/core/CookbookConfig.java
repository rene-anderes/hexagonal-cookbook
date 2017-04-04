package org.anderes.edu.hexagonal.cookbook.core;

import org.anderes.edu.hexagonal.cookbook.mediation.UserInterfaceRecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookbookConfig {

    @Bean
    /*package*/ RepositoryRecipeService getRepositoryService() {
        return new RepositoryRecipeService(getValidatorServeice(), getNotificationService());
    }
    
    @Bean
    /*package*/ ValidatorService getValidatorServeice() {
        return new ValidatorService();
    }
    
    @Bean
    /*package*/ UserInterfaceRecipeService getUserInterfaceRecipeService() {
        return new UserInterfaceRecipeServiceImpl(getRepositoryService(), getValidatorServeice());
    }
    
    @Bean
    /*package*/ NotificationService getNotificationService() {
        return new NotificationService();
    }
}
