package nl.vdberg.recipesmanager.core.converter;

import nl.vdberg.recipesmanager.core.configuation.TestApplication;
import nl.vdberg.recipesmanager.core.domain.Ingredients;
import nl.vdberg.recipesmanager.core.domain.json.JsonIngredients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestApplication.class)
class IngredientsConverterTest {

    @Resource
    private IngredientsConverter ingredientsConverter;

    @Test
    @DisplayName("IngredientsConverter Test toServer")
    void toServerTest() {

        JsonIngredients jsonIngredients = JsonIngredients.builder()
                .quantity("500ml")
                .name("Coconut water")
                .build();

        Ingredients ingredients = ingredientsConverter.toServer(jsonIngredients);

        assertNotNull(ingredients, "Ingredients is null");
        assertEquals("Coconut water", ingredients.getName(), "Name is not equal");
        assertEquals("500ml", ingredients.getQuantity(), "Quantity is null");

    }

    @Test
    @DisplayName("IngredientsConverter Test toClient")
    void toClientTest() {

        Ingredients ingredients = Ingredients.builder()
                .quantity("500g")
                .name("Chicken")
                .build();

        JsonIngredients jsonIngredients = ingredientsConverter.toClient(ingredients);

        assertNotNull(ingredients, "Recipe is null");
        assertEquals("Chicken", ingredients.getName(), "Name is not equal");
        assertEquals("500g", ingredients.getQuantity(), "Quantity is not equal");

    }

}
