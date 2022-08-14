package nl.vdberg.recipesmanager.core.repository;

import nl.vdberg.recipesmanager.core.configuation.TestApplication;
import nl.vdberg.recipesmanager.core.domain.Recipe;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeRepositoryTest {

    @Resource
    private RecipeRepository recipeRepository;

    @Test
    @DisplayName("Save Recipe")
    @Order((1))
    @Rollback(value = false)
    void saveRecipe() {

        Recipe recipe = Recipe.builder()
                .servingSize(5)
                .name("Tuna Dish")
                .vegetarian(false)
                .build();

        recipeRepository.save(recipe);

        assertNotNull(recipe.getId());
        assertEquals(1, recipe.getId(), "Recipe ID not null");
    }

    @Test
    @DisplayName("Get Recipe")
    @Order(2)
    void getRecipe() {

        Recipe recipe = recipeRepository.findById(1L).get();

        assertNotNull(recipe, "Recipe is null");

    }

    @Test
    @DisplayName("Get all Recipes")
    @Order(3)
    void getAllRecipes() {
        List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();

        assertNotEquals(0, recipes.size());
    }

    @Test
    @DisplayName("Update Recipes")
    @Order(4)
    @Rollback(value = false)
    void updateRecipe() {

        Recipe recipe = recipeRepository.findById(1L).get();

        recipe.setName("Chicken Dish");

        Recipe updateRecipe = recipeRepository.save(recipe);

        assertEquals("Chicken Dish", updateRecipe.getName(), "Names are not equal");

    }

    @Test
    @DisplayName("Delete Recipes")
    @Order(5)
    @Rollback(value = false)
    void deleteRecipe() {

        Recipe recipe = recipeRepository.findById(1L).get();
        recipeRepository.delete(recipe);

        Recipe recipe1 = null;

        Optional<Recipe> optionalRecipe = recipeRepository.findById(1L);

        if (optionalRecipe.isPresent()) {
            recipe1 = optionalRecipe.get();
        }

        assertNull(recipe1, "Recipe is not null");

    }
}