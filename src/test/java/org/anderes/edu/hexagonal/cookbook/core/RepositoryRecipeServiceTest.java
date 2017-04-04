package org.anderes.edu.hexagonal.cookbook.core;

import static java.time.Month.MARCH;
import static org.anderes.edu.hexagonal.cookbook.core.RecipeBuilder.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.anderes.edu.hexagonal.cookbook.core.CookbookConfig;
import org.anderes.edu.hexagonal.cookbook.core.MasterControlProgram;
import org.anderes.edu.hexagonal.cookbook.core.RepositoryRecipeService;
import org.anderes.edu.hexagonal.cookbook.domain.IngredientDomainObject;
import org.anderes.edu.hexagonal.cookbook.domain.NutritiveValueDomanObject;
import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.mediation.CookbookException;
import org.anderes.edu.hexagonal.cookbook.port.NotificationPort;
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
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CookbookConfig.class })
public class RepositoryRecipeServiceTest {

    @Inject
    private RepositoryRecipeService repositoryService;
    @Mock
    private RepositoryPort repositoryPort;
    @Mock
    private NotificationPort notificationPort;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(repositoryPort.getVersion()).thenReturn("Mock-Object for Testing");
        MasterControlProgram.getInstance().registerRepositoryPort(repositoryPort);
        MasterControlProgram.getInstance().registerNotificationPort(notificationPort);
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
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verifyZeroInteractions(notificationPort);
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
        verifyZeroInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeFindNotValidRecipe() {
        // given
        final RecipeDomainObject recipe = createNotValidRecipe();
        when(repositoryPort.findRecipeById("12345678-1234-1234-4321-123456789abc"))
                            .thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        exception.expect(CookbookException.class);
        exception.expectMessage("constraint violation exception");
        
        // when
        repositoryService.findRecipeById("12345678-1234-1234-4321-123456789abc", repositoryPort);
        verify(repositoryPort, times(1)).findRecipeById("12345678-1234-1234-4321-123456789abc");
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verifyZeroInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeUpdateRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        
        // when
        repositoryService.updateRecipe(recipe, repositoryPort);
        
        // verify
        assertThat(recipe.getAddingDate(), is(LocalDateTime.of(2017, MARCH, 27, 16, 00)));
        assertThat(LocalDateTime.now().minusSeconds(2).isBefore(recipe.getEditingDate()), is(true));
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort, times(1)).updateRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verify(notificationPort, times(1)).eventUpdateRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeIllegalStateByUpdateRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        exception.expect(CookbookException.class);
        exception.expectMessage("illegal state exception");
        
        // when
        repositoryService.updateRecipe(recipe, repositoryPort);
    }
    
    @Test
    public void shouldBeAddNewRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.empty());
        
        // when
        repositoryService.addNewRecipe(recipe, repositoryPort);
        
        // verify
        assertThat(LocalDateTime.now().minusSeconds(2).isBefore(recipe.getAddingDate()), is(true));
        assertThat(LocalDateTime.now().minusSeconds(2).isBefore(recipe.getEditingDate()), is(true));
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort, times(1)).addNewRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verify(notificationPort, times(1)).eventAddNewRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeBulkAddRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.empty());
        
        // when
        repositoryService.bulkAddRecipe(recipe, repositoryPort);
        
        // verify
        assertThat(recipe.getAddingDate().isEqual(LocalDateTime.of(2017, MARCH, 27, 16, 00)), is(true));
        assertThat(recipe.getEditingDate().isEqual(LocalDateTime.of(2017, MARCH, 27, 16, 32)), is(true));
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort, times(1)).addNewRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verify(notificationPort, times(1)).eventAddNewRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
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
    }
    
    @Test
    public void shouldBeRemoveRecipe() {
        // given
        final RecipeDomainObject recipe = createRecipe();
        when(repositoryPort.findRecipeById(recipe.getId())).thenReturn(Optional.of(SerializationUtils.clone(recipe)));
        
        // when
        repositoryService.removeRecipe(recipe, repositoryPort);
        
        // then
        verify(repositoryPort, times(1)).findRecipeById(recipe.getId());
        verify(repositoryPort, times(1)).removeRecipe(recipe);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
        verify(notificationPort, times(1)).eventRemoveRecipe(recipe);
        verifyNoMoreInteractions(notificationPort);
    }
    
    @Test
    public void shouldBeRecipeCollectionByTag() {
        // given
        final Set<String> tags = Stream.of("vegetarisch", "indisch").collect(Collectors.toSet());
        final Set<RecipeDomainObject> recipeSet = createRandomRecipeSet(2);
        when(repositoryService.findRecipesByTags(tags, repositoryPort)).thenReturn(recipeSet);
        
        // when
        final Set<RecipeDomainObject> recipes = repositoryService.findRecipesByTags(tags , repositoryPort);
        
        // then
        assertThat(recipes, is(not(nullValue())));
        assertThat(recipes.size(), is(2));
        verify(repositoryPort, times(1)).findRecipesByTags(tags);
        verify(repositoryPort).getVersion();
        verifyNoMoreInteractions(repositoryPort);
    }
    
    @Test
    public void shouldBeNotValidRecipeCollectionByTag() {
        // given
        final Set<String> tags = Stream.of("vegetarisch", "indisch").collect(Collectors.toSet());
        final Set<RecipeDomainObject> recipeSet = createRandomRecipeSet(2);
        recipeSet.add(createNotValidRecipe());
        when(repositoryService.findRecipesByTags(tags, repositoryPort)).thenReturn(recipeSet);
        exception.expect(CookbookException.class);
        exception.expectMessage("constraint violation exception");
        
        // when
        repositoryService.findRecipesByTags(tags , repositoryPort);
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
