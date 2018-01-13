package org.anderes.edu.hexagonal.cookbook.core;

import static org.anderes.edu.hexagonal.cookbook.core.RecipeBuilder.createRecipe;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import javax.inject.Inject;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.port.NotificationPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CookbookConfig.class })
public class NotificationServiceTest {

    @Mock private NotificationPort notificationPort;
    @Inject
    private NotificationService notificationService;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MasterControlProgram.getInstance().registerNotificationPort(notificationPort);
    }
    
    @Test
    public void shouldBeNewRecipeEvent() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        // when
        notificationService.eventNewRecipe(recipe);
        // then
        verify(notificationPort, times(1)).eventAddNewRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeUpdateRecipeEvent() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        // when
        notificationService.eventUpdateRecipe(recipe);
        // then
        verify(notificationPort, times(1)).eventUpdateRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeRemoveRecipeEvent() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        // when
        notificationService.eventRemoveRecipe(recipe);
        // then
        verify(notificationPort, times(1)).eventRemoveRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
    }
}
