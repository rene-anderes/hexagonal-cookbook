package org.anderes.edu.hexagonal.cookbook.core;

import static java.time.Month.MARCH;
import static org.anderes.edu.hexagonal.cookbook.core.RecipeBuilder.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.anderes.edu.hexagonal.cookbook.core.CookbookConfig;
import org.anderes.edu.hexagonal.cookbook.core.MasterControlProgram;
import org.anderes.edu.hexagonal.cookbook.domain.IngredientDomainObject;
import org.anderes.edu.hexagonal.cookbook.domain.NutritiveValueDomanObject;
import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.mediation.CookbookException;
import org.anderes.edu.hexagonal.cookbook.mediation.UserInterfaceRecipeService;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.anderes.edu.hexagonal.cookbook.port.UserInterfacePort;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CookbookConfig.class })
public class UserInterfaceRecipeServiceTest {

    @Mock
    private UserInterfacePort userInterfacePort;
    @Mock
    private RepositoryPort repositoryPort;
    @Inject
    private UserInterfaceRecipeService userInterfaceService;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(repositoryPort.getVersion()).thenReturn("Mock-Object for Testing");
        MasterControlProgram.getInstance().registerRepositoryPort(repositoryPort);
    }

    @Test
    public void shouldBeGetRecipeById() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(recipe));

        // when
        userInterfaceService.getRecipeById(recipe.getId(), userInterfacePort);
        
        // then
        verify(userInterfacePort).showRecipe(recipe);
        verify(repositoryPort).findRecipeById(recipe.getId());
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verifyNoMoreInteractions(userInterfacePort);
    }
    
    @Test
    public void shouldBeSetRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(recipe));
        
        // when
        userInterfaceService.setRecipe(recipe);
        
        // then
        verifyNoMoreInteractions(userInterfacePort);
        verify(repositoryPort).findRecipeById(recipe.getId());
        verify(repositoryPort).updateRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeSetNotValidRecipe() {
        // given
        final RecipeDomainObject recipe = createNotValidRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(recipe));
        exception.expect(CookbookException.class);
        exception.expectMessage("constraint violation exception");
        
        // when
        userInterfaceService.setRecipe(recipe);
        
        // then
        verify(repositoryPort).findRecipeById(recipe.getId());
        verify(repositoryPort).updateRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
        
    @Test
    public void shouldBeAddNewRecipe() {
        //given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.empty());
        
        // when
        userInterfaceService.addRecipe(recipe);
        
        // then
        verifyZeroInteractions(userInterfacePort);
        verify(repositoryPort).findRecipeById(recipe.getId());
        verify(repositoryPort).addNewRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeBulkAddRecipe() {
        //given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.empty());
        
        // when
        userInterfaceService.bulkAddRecipe(recipe);
        
        // then
        assertThat(recipe.getAddingDate().isEqual(LocalDateTime.of(2017, MARCH, 27, 16, 00)), is(true));
        assertThat(recipe.getEditingDate().isEqual(LocalDateTime.of(2017, MARCH, 27, 16, 32)), is(true));
        verifyZeroInteractions(userInterfacePort);
        verify(repositoryPort).findRecipeById(recipe.getId());
        verify(repositoryPort).addNewRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeRecipeOverview() {
        // given
        final Map<String, String> values = new HashMap<>(2);
        values.put("12345678-1234-1234-4321-123456789abc", "Pasta Siziliana");
        values.put("12345678-1234-1234-4321-123456789def", "Pasta Florentina");
        when(repositoryPort.getRecipeOverview()).thenReturn(values);
        
        // when
        userInterfaceService.getRecipeOverview(userInterfacePort);
        
        // then
        verify(userInterfacePort, times(1)).showRecipeOverview(values);
        verify(repositoryPort, times(1)).getRecipeOverview();
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(userInterfacePort);
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeRecipesByTags() {
        // given
        final Set<RecipeDomainObject> recipeSet = createRandomRecipeSet(3);
        final Set<String> tags = Stream.of("vegetarisch", "indisch").collect(Collectors.toSet());;
        when(repositoryPort.findRecipesByTags(tags)).thenReturn(recipeSet);
        
        // when
        userInterfaceService.getRecipesByTags(tags, userInterfacePort);
        
        // then
        verify(userInterfacePort, times(1)).showRecipes(recipeSet);
        verify(repositoryPort, times(1)).findRecipesByTags(tags);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(userInterfacePort);
        verifyNoMoreInteractions(repositoryPort);
    }
    
    private RecipeDomainObject createNotValidRecipe() {
        final RecipeDomainObject recipe = new RecipeDomainObject("12345678-1234-1234-4321-123456789abc");
        recipe.setTitle("Curry-Spinat mit Mandel-Kartoffeln")
            .setNutritiveValue(new NutritiveValueDomanObject(572, 18, 37, 35))
            .setRating(4)
            .setAddingDate(LocalDateTime.of(2017, MARCH, 27, 16, 00))
            .setEditingDate(LocalDateTime.of(2017, MARCH, 27, 16, 32))
            .addIngredient(new IngredientDomainObject("400 g", "Kartoffeln", "klein festkochend"))
            .addIngredient(new IngredientDomainObject("500 g", "Blattspinat frisch", "siehe Rezept-Tipp"));
        return recipe;
    }
}
