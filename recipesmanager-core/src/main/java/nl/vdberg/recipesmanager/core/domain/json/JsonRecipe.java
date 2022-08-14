package nl.vdberg.recipesmanager.core.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRecipe {

    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int servingSize;

    @NonNull
    private boolean vegetarian;

    List<JsonIngredients> ingredients;

    List<JsonInstructions> instructions;


}
