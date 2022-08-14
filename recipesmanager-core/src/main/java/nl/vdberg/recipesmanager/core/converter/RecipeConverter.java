package nl.vdberg.recipesmanager.core.converter;

import nl.vdberg.recipesmanager.core.domain.Ingredients;
import nl.vdberg.recipesmanager.core.domain.Instructions;
import nl.vdberg.recipesmanager.core.domain.Recipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonIngredients;
import nl.vdberg.recipesmanager.core.domain.json.JsonInstructions;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecipeConverter {

    @Resource
    IngredientsConverter ingredientsConverter;

    @Resource
    InstructionsConverter instructionsConverter;

    public Recipe toServer(JsonRecipe jsonRecipe) {

        List<Ingredients> ingredients = null;
        List<Instructions> instructions = null;

        if (jsonRecipe.getIngredients() != null) {

            ingredients = jsonRecipe.getIngredients()
                    .stream()
                    .map(item -> ingredientsConverter.toServer(item))
                    .toList();
        }

        if (jsonRecipe.getInstructions() != null) {
            instructions = jsonRecipe.getInstructions()
                    .stream().map(item -> instructionsConverter.toServer(item))
                    .toList();

        }
        return Recipe.builder()
                .vegetarian(jsonRecipe.isVegetarian())
                .name(jsonRecipe.getName())
                .servingSize(jsonRecipe.getServingSize())
                .ingredients(ingredients)
                .instructions(instructions)
                .build();
    }

    public JsonRecipe toClient(Recipe recipe) {

        List<JsonIngredients> ingredients = null;
        List<JsonInstructions> instructions = null;

        if (recipe.getIngredients() != null) {
            ingredients = recipe.getIngredients()
                    .stream()
                    .map(item -> ingredientsConverter.toClient(item))
                    .toList();
        }

        if (recipe.getInstructions() != null) {
            instructions = recipe.getInstructions()
                    .stream()
                    .map(item -> instructionsConverter.toClient(item))
                    .toList();
        }
        return JsonRecipe.builder()
                .id(recipe.getId())
                .vegetarian(recipe.isVegetarian())
                .name(recipe.getName())
                .servingSize(recipe.getServingSize())
                .ingredients(ingredients)
                .instructions(instructions)
                .build();
    }
}
