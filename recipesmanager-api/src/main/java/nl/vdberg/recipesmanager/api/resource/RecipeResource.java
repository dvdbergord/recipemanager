package nl.vdberg.recipesmanager.api.resource;

import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipeSearch;
import nl.vdberg.recipesmanager.core.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class RecipeResource {

    @Resource
    RecipeService recipeService;

    @GetMapping("/recipe")
    public ResponseEntity<List<JsonRecipe>> getAllRecipes(@RequestBody JsonRecipeSearch jsonRecipeSearch) {

        List<JsonRecipe> jsonRecipes = recipeService.findAllRecipes(jsonRecipeSearch);
        return new ResponseEntity<>(jsonRecipes, HttpStatus.OK);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<JsonRecipe> getSingleRecipe(@PathVariable long id) {

        JsonRecipe jsonRecipe = recipeService.findRecipeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(jsonRecipe);
    }


    @PostMapping("/recipe")
    public ResponseEntity<JsonRecipe> createRecipes(@Valid @RequestBody JsonRecipe jsonRecipe) {

        JsonRecipe createdRecipe = recipeService.createRecipe(jsonRecipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<JsonRecipe> updateRecipe(@PathVariable("id") long id, @Valid @RequestBody JsonRecipe jsonRecipe) {

        JsonRecipe updatedRecipe = recipeService.updateRecipe(id, jsonRecipe);

        return ResponseEntity.status(HttpStatus.OK).body(updatedRecipe);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<JsonRecipe> deleteRecipes(@PathVariable("id") long id) {

        recipeService.deleteRecipe(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
