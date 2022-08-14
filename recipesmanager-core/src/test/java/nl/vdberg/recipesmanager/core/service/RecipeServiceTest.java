package nl.vdberg.recipesmanager.core.service;

import nl.vdberg.recipesmanager.core.configuation.TestApplication;
import nl.vdberg.recipesmanager.core.domain.Recipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipeSearch;
import nl.vdberg.recipesmanager.core.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestApplication.class)
class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;
    private Recipe recipe;
    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private EntityManager entityManager;

    @Test
    @DisplayName("Update Recipe")
    void updateRecipe() {

        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Steak")
                .servingSize(4)
                .vegetarian(false)
                .instructions(null)
                .ingredients(null)
                .build();

        recipe = Recipe.builder()
                .id(1L)
                .name("Meat")
                .servingSize(5)
                .vegetarian(true)
                .build();

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.ofNullable((recipe)));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        assertDoesNotThrow(() -> recipeService.updateRecipe(1L, jsonRecipe));

    }

    @Test
    @DisplayName("Create Recipe")
    void createRecipe() {

        recipe = Recipe.builder()
                .id(1L)
                .name("Meat")
                .servingSize(5)
                .vegetarian(true)
                .build();

        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Steak")
                .servingSize(4)
                .vegetarian(false)
                .instructions(null)
                .ingredients(null)
                .build();


        when(recipeRepository.save(any(Recipe.class)))
                .thenReturn(recipe);

        assertDoesNotThrow(() -> recipeService.createRecipe(jsonRecipe));

    }

    @Test
    @DisplayName("Find Recipe by ID")
    void findRecipeById() {

        recipe = Recipe.builder()
                .id(1L)
                .name("Meat")
                .servingSize(5)
                .vegetarian(true)
                .build();

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.ofNullable((recipe)));

        JsonRecipe jsonRecipe1 = recipeService.findRecipeById(1L);

        assertNotNull(jsonRecipe1, "Return object is null");
        assertEquals("Meat", recipe.getName(), "Name is not equal");
        assertEquals(5, recipe.getServingSize(), "Serving size is not equal");
        assertTrue(recipe.isVegetarian(), "Vegetarian is not equal");

    }

    @Test
    @DisplayName("Delete Recipe")
    void deleteRecipe() {

        recipe = Recipe.builder()
                .id(1L)
                .name("Meat")
                .servingSize(5)
                .vegetarian(true)
                .build();

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.ofNullable((recipe)));

        doNothing().when(recipeRepository).deleteById(1L);

        assertDoesNotThrow(() -> recipeService.deleteRecipe(1L));

    }

    @Test
    @DisplayName("Return all recipes")
    void findAllRecipes() {

        JsonRecipeSearch jsonRecipeSearch = JsonRecipeSearch.builder()
                .servings(1L)
                .vegetarian(true)
                .build();

        Query mockedQuery = mock(Query.class);
        when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockedQuery);

        when(mockedQuery.getResultList()).thenReturn((List) recipe);

        List<JsonRecipe> jsonRecipes = recipeService.findAllRecipes(jsonRecipeSearch);

        assertNotNull(jsonRecipes);
    }
}