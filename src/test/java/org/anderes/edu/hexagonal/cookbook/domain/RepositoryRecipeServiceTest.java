package org.anderes.edu.hexagonal.cookbook.domain;

import static java.time.Month.MARCH;
import static org.anderes.edu.hexagonal.cookbook.domain.RecipeBuilder.createRecipe;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import org.anderes.edu.hexagonal.cookbook.mediation.CookbookException;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.apache.commons.lang3.SerializationUtils;

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
public class RepositoryRecipeServiceTest {

    @Inject
    private RepositoryRecipeService repositoryService;
    @Mock
    private RepositoryPort repositoryPort;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(repositoryPort.getVersion()).thenReturn("Mock-Object for Testing");
        MasterControlProgram.getInstance().registerRepositoryPort(repositoryPort);
    }
    
    @Test
    public void shouldBeFindById() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        
        // when
        final RecipeDomainObject findRecipe = repositoryService.findRecipeById(recipe.getId(), repositoryPort);
        
        // then
        assertThat(findRecipe.equals(recipe), is(true));
    }
    
    @Test
    public void shouldBeNotFindById() {
        // given
        when(repositoryPort.findRecipeById("Wrong-ID")).thenReturn(Optional.empty());
        exception.expect(CookbookException.class);
        exception.expectMessage("no result exception");
        
        // when
        repositoryService.findRecipeById("Wrong-ID", repositoryPort);
        verify(repositoryPort, times(1)).findRecipeById("Wrong-ID");
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeFindNotValidRecipe() {
        // given
        final RecipeDomainObject recipe = createNotValidRecipe();
        when(repositoryPort.findRecipeById("12345678-1234-1234-4321-123456789abc")).thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        exception.expect(CookbookException.class);
        exception.expectMessage("constraint violation exception");
        
        // when
        repositoryService.findRecipeById("12345678-1234-1234-4321-123456789abc", repositoryPort);
        verify(repositoryPort, times(1)).findRecipeById("12345678-1234-1234-4321-123456789abc");
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeUpdateRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        
        // when
        repositoryService.updateRecipe(recipe, repositoryPort);
        
        // verify
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort, times(1)).updateRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeIllegalStateByUpdateRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        exception.expect(CookbookException.class);
        exception.expectMessage("illegal state exception");
        
        // when
        repositoryService.updateRecipe(recipe, repositoryPort);
        
        // verify
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeAddNewRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.empty());
        
        // when
        repositoryService.addNewRecipe(recipe, repositoryPort);
        
        // verify
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort, times(1)).addNewRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeIllegalStateByAddNewRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        exception.expect(CookbookException.class);
        exception.expectMessage("illegal state exception");
        
        // when
        repositoryService.addNewRecipe(recipe, repositoryPort);
        
        // verify
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort).getVersion();
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
