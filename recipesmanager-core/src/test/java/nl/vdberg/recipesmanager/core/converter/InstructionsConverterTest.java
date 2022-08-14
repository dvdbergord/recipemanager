package nl.vdberg.recipesmanager.core.converter;

import nl.vdberg.recipesmanager.core.configuation.TestApplication;
import nl.vdberg.recipesmanager.core.domain.Instructions;
import nl.vdberg.recipesmanager.core.domain.json.JsonInstructions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestApplication.class)
class InstructionsConverterTest {

    @Resource
    private InstructionsConverter instructionsConverter;

    @Test
    @DisplayName("InstructionsConverter Test toServer")
    void toServerTest() {

        JsonInstructions jsonInstructions = JsonInstructions.builder()
                .step("Step 1")
                .description("Bake in Oven")
                .build();


        Instructions instructions = instructionsConverter.toServer(jsonInstructions);

        assertNotNull(instructions, "Instructions is null");
        assertEquals("Step 1", instructions.getStep(), "Step is null");
        assertEquals("Bake in Oven", instructions.getDescription(), "Description is incorrect");


    }

    @Test
    @DisplayName("InstructionsConverter Test toClient")
    void toClientTest() {

        Instructions instructions = Instructions.builder()
                .description("Boil at 180 degrees")
                .step("Step 1")
                .build();

        JsonInstructions jsonInstructions = instructionsConverter.toClient(instructions);

        assertNotNull(jsonInstructions, "Instructions is null");
        assertEquals("Boil at 180 degrees", jsonInstructions.getDescription(), "Name is not equal");
        assertEquals("Step 1", jsonInstructions.getStep(), "Step is not equal");


    }

}