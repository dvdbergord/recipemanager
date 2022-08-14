package nl.vdberg.recipesmanager.core.converter;

import nl.vdberg.recipesmanager.core.domain.Ingredients;
import nl.vdberg.recipesmanager.core.domain.json.JsonIngredients;
import org.springframework.stereotype.Service;

@Service
public class IngredientsConverter {

    public Ingredients toServer(JsonIngredients ingredients) {

        return Ingredients.builder()
                .name(ingredients.getName())
                .quantity(ingredients.getQuantity())
                .build();
    }

    public JsonIngredients toClient(Ingredients ingredients) {

        return JsonIngredients.builder()
                .id(ingredients.getId())
                .name(ingredients.getName())
                .quantity(ingredients.getQuantity())
                .build();
    }

}
