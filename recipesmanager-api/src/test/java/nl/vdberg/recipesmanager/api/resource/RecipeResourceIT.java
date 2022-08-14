package nl.vdberg.recipesmanager.api.resource;

import nl.vdberg.recipesmanager.core.domain.json.JsonIngredients;
import nl.vdberg.recipesmanager.core.domain.json.JsonInstructions;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipeSearch;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestApplication.class)
@Import(RecipeResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeResourceIT {

    @Resource
    RecipeResource recipeResource;

    @Test
    @DisplayName("Create recipe")
    @Order((1))
    @Rollback(value = false)
    void testCreate() {
        JsonInstructions jsonInstructions = JsonInstructions.builder()
                .description("Defrost")
                .step("Step 1")
                .build();
        JsonIngredients jsonIngredients = JsonIngredients.builder()
                .name("Green Peppers")
                .quantity("500ml")
                .build();
        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Veggie Dish")
                .vegetarian(true)
                .servingSize(4)
                .instructions(Collections.singletonList(jsonInstructions))
                .ingredients(Collections.singletonList(jsonIngredients))
                .build();

        JsonRecipe createdRecipe = recipeResource.createRecipes(jsonRecipe).getBody();

        assertNotNull(createdRecipe, "Returned object is null");
        assertEquals("Veggie Dish", createdRecipe.getName(), "Name is incorrect");
        assertNotNull(createdRecipe.getId(), "Created ID is null");

    }

    @Test
    @DisplayName("Get recipe by ID")
    @Order((2))
    void testGetRecipeById() {
        JsonRecipe jsonRecipe = recipeResource.getSingleRecipe(1L).getBody();

        assertNotNull(jsonRecipe, "Recipe is null");
        assertEquals(4, jsonRecipe.getServingSize(), "Serving size not equal");
        assertEquals("Veggie Dish", jsonRecipe.getName(), "Serving size not equal");
    }

    @Test
    @DisplayName("Update recipe")
    @Order(3)
    @Rollback(value = false)
    void testUpdateRecipe() {
        JsonInstructions jsonInstructions = JsonInstructions.builder()
                .description("Defrost")
                .step("Step 1")
                .build();
        JsonIngredients jsonIngredients = JsonIngredients.builder()
                .name("Green Peppers")
                .quantity("500ml")
                .build();
        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Veggie Surprise Dish")
                .vegetarian(true)
                .servingSize(4)
                .instructions(Collections.singletonList(jsonInstructions))
                .ingredients(Collections.singletonList(jsonIngredients))
                .build();

        JsonRecipe jsonRecipeUpdated = recipeResource.updateRecipe(1L, jsonRecipe).getBody();

        assertEquals("Veggie Surprise Dish", jsonRecipe.getName(), "Dish name not equal");

    }

    @Test
    @DisplayName("Create another recipe")
    @Order((4))
    @Rollback(value = false)
    void testCreateAnotherRecipe() {
        JsonInstructions jsonInstructions = JsonInstructions.builder()
                .description("Pre heat oven to 180 degrees")
                .step("Step 1")
                .build();
        JsonIngredients jsonIngredients = JsonIngredients.builder()
                .name("Steak")
                .quantity("500grams")
                .build();
        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Steak Dish")
                .vegetarian(false)
                .servingSize(4)
                .instructions(Collections.singletonList(jsonInstructions))
                .ingredients(Collections.singletonList(jsonIngredients))
                .build();

        JsonRecipe createdRecipe = recipeResource.createRecipes(jsonRecipe).getBody();

        assertNotNull(createdRecipe, "Returned object is null");
        assertEquals("Steak Dish", createdRecipe.getName(), "Name is incorrect");
        assertNotNull(createdRecipe.getId(), "Created ID is null");
        assertEquals(6L, createdRecipe.getId(), "IDS not equal");

    }

    @Test
    @DisplayName("Get recipe by Search query")
    @Order(5)
    void testGetRecipeBySearch() {
        JsonRecipeSearch jsonRecipeSearch = JsonRecipeSearch.builder()
                .searchInstructions("Pre heat oven to 180 degrees")
                .includeSearch("Steak")
                .excludeSearch("Veggie")
                .servings(4)
                .vegetarian(false)
                .build();

        List<JsonRecipe> jsonRecipes = recipeResource.getAllRecipes(jsonRecipeSearch).getBody();

        assertNotNull(jsonRecipes, "Recipe is null");
        assertEquals(1, jsonRecipes.size(), "Returned results not equal");
        assertEquals("Steak Dish", jsonRecipes.get(0).getName(), "Returned Recipe name not equal");
    }

    @Test
    @DisplayName("Delete recipe")
    @Order(6)
    void testDelete() {
        ResponseEntity responseEntity = recipeResource.deleteRecipes(6L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT.value(), responseEntity.getStatusCodeValue(), "Status code not equal");
    }

}
