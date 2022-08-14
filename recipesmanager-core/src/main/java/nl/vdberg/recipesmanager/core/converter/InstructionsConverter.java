package nl.vdberg.recipesmanager.core.converter;

import nl.vdberg.recipesmanager.core.domain.Instructions;
import nl.vdberg.recipesmanager.core.domain.json.JsonInstructions;
import org.springframework.stereotype.Service;

@Service
public class InstructionsConverter {

    public Instructions toServer(JsonInstructions jsonRecipe) {

        return Instructions.builder()
                .description(jsonRecipe.getDescription())
                .step(jsonRecipe.getStep())
                .build();
    }

    public JsonInstructions toClient(Instructions instructions) {

        return JsonInstructions.builder()
                .id(instructions.getId())
                .description(instructions.getDescription())
                .step(instructions.getStep())
                .build();
    }

}
