package nl.vdberg.recipesmanager.core.converter;

import nl.vdberg.recipesmanager.core.configuation.TestApplication;
import nl.vdberg.recipesmanager.core.domain.Ingredients;
import nl.vdberg.recipesmanager.core.domain.Instructions;
import nl.vdberg.recipesmanager.core.domain.Recipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonIngredients;
import nl.vdberg.recipesmanager.core.domain.json.JsonInstructions;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestApplication.class)
class RecipeConverterTest {

    @Resource
    private RecipeConverter recipeConverter;

    @Test
    @DisplayName("RecipeConverter Test toServer")
    void toServerTest() {
        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Recipe Name")
                .instructions(Collections.singletonList(JsonInstructions.builder().description("Desc").step("Step 1").build()))
                .vegetarian(true)
                .ingredients(Collections.singletonList(JsonIngredients.builder().name("Name").quantity("500ml").build()))
                .servingSize(6)
                .build();

        Recipe recipe = recipeConverter.toServer(jsonRecipe);

        assertNotNull(recipe, "Recipe is null");
        assertEquals("Recipe Name", recipe.getName(), "Name is not equal");
        assertEquals(6, recipe.getServingSize(), "Serving size is not equal");
        assertTrue(recipe.isVegetarian(), "Vegetarian is null");


    }

    @Test
    @DisplayName("RecipeConverter Test toClient")
    void toClientTest() {
        Recipe recipe = Recipe.builder()
                .name("Recipe Name")
                .vegetarian(true)
                .ingredients(Collections.singletonList(Ingredients.builder().name("Name").quantity("500ml").build()))
                .instructions(Collections.singletonList(Instructions.builder().description("Desc").step("Step 1").build()))
                .servingSize(12)
                .build();

        JsonRecipe jsonRecipe = recipeConverter.toClient(recipe);

        assertNotNull(jsonRecipe, "Recipe is null");
        assertEquals("Recipe Name", jsonRecipe.getName(), "Name is not equal");
        assertEquals(12, jsonRecipe.getServingSize(), "Serving size is not equal");
        assertTrue(jsonRecipe.isVegetarian(), "Vegetarian is null");

    }

}