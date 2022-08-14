package nl.vdberg.recipesmanager.api.resource;

import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipeSearch;
import nl.vdberg.recipesmanager.core.repository.IngredientsRepository;
import nl.vdberg.recipesmanager.core.repository.RecipeRepository;
import nl.vdberg.recipesmanager.core.service.RecipeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RecipeResource.class)
@WebMvcTest(RecipeResource.class)
class RecipeResourceTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private RecipeService recipeService;

    @MockBean
    private IngredientsRepository ingredientsRepository;

    @MockBean
    private RecipeRepository recipeRepository;


    @Autowired
    private WebApplicationContext context;

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get Recipe with ID")
    void retrieveReceipeWithID() throws Exception {

        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Test dish")
                .vegetarian(true)
                .servingSize(5)
                .id(1L)
                .build();
        when(recipeService.findRecipeById(1L)).thenReturn(jsonRecipe);

        mockMvc.perform(get("/api/recipe/1"))
                .andExpect(jsonPath("$.name", Matchers.is("Test dish")))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Delete Recipe")
    void deleteRecipe() throws Exception {

        doNothing().when(recipeService).deleteRecipe(1L);

        mockMvc.perform(delete("/api/recipe/1")
                        .with(csrf().asHeader()))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all recipes")
    void retrieveRecipes() throws Exception {
        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .name("Test dish")
                .vegetarian(true)
                .servingSize(5)
                .id(1L)
                .build();
        List<JsonRecipe> jsonRecipeList = new ArrayList<>();
        jsonRecipeList.add(jsonRecipe);
        jsonRecipeList.add(jsonRecipe);

        when(recipeService.findAllRecipes(any(JsonRecipeSearch.class))).thenReturn((jsonRecipeList));

        mockMvc.perform(get("/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Test dish")))
                .andExpect(jsonPath("$[1].servingSize", Matchers.is(5)));

    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Create Recipe")
    void createRecipe() throws Exception {

        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .id(3L)
                .name("Chicken Dish")
                .servingSize(5)
                .vegetarian(true)
                .ingredients(null)
                .instructions(null)
                .build();

        when(recipeService.createRecipe(any(JsonRecipe.class))).thenReturn(jsonRecipe);

        mockMvc.perform(post("/api/recipe")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"Namessss21\",\n" +
                                "    \"servingSize\": 45,\n" +
                                "    \"vegetarian\": true,\n" +
                                "    \"ingredients\": [\n" +
                                "        {\n" +
                                "            \"id\": 1,\n" +
                                "            \"name\": \"Steak\",\n" +
                                "            \"quantity\": \"500 ml\"\n" +
                                "        }\n" +
                                "    ],\n" +
                                "    \"instructions\": [\n" +
                                "        {\n" +
                                "            \"step\": 1,\n" +
                                "            \"description\": \"Wash Leaves\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}\n" +
                                "\n" +
                                "\n"))
                .andExpect(jsonPath("$.name", Matchers.is("Chicken Dish")))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Update recipe")
    void updateRecipe() throws Exception {

        JsonRecipe jsonRecipe = JsonRecipe.builder()
                .id(3L)
                .name("Chicken Dish")
                .servingSize(5)
                .vegetarian(true)
                .ingredients(null)
                .instructions(null)
                .build();

        when(recipeService.updateRecipe(any(Long.class), any(JsonRecipe.class))).thenReturn(jsonRecipe);

        mockMvc.perform(put("/api/recipe/1")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"Namessss21\",\n" +
                                "    \"servingSize\": 45,\n" +
                                "    \"vegetarian\": true,\n" +
                                "    \"ingredients\": [\n" +
                                "        {\n" +
                                "            \"id\": 1,\n" +
                                "            \"name\": \"Steak\",\n" +
                                "            \"quantity\": \"500 ml\"\n" +
                                "        }\n" +
                                "    ],\n" +
                                "    \"instructions\": [\n" +
                                "        {\n" +
                                "            \"step\": 1,\n" +
                                "            \"description\": \"Wash Leaves\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}\n" +
                                "\n" +
                                "\n"))
                .andExpect(status().isOk());

    }

}