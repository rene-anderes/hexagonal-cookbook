package org.anderes.edu.hexagonal.cookbook.mediation;

import javax.inject.Inject;

import org.anderes.edu.hexagonal.cookbook.domain.CookbookConfig;
import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.anderes.edu.hexagonal.cookbook.port.UserInterfacePort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.anderes.edu.hexagonal.cookbook.domain.RecipeBuilder.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CookbookConfig.class })
public class UserInterfaceRecipeServiceTest {

    @Mock
    private UserInterfacePort userInterfacePort;
    @Mock
    private RepositoryPort repositoryPort;
    @Inject
    private UserInterfaceRecipeService userInterfaceService;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MasterControlProgramm.getInstance().registerRepositoryPort(repositoryPort);
    }

    @Test
    public void shouldBeGetRecipeById() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(recipe);

        // when
        userInterfaceService.getRecipeById(recipe.getId(), userInterfacePort);
        
        // then
        verify(userInterfacePort).showRecipe(recipe);
    }
}
