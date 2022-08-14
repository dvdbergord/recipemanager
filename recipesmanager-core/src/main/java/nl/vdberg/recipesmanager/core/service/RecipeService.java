package nl.vdberg.recipesmanager.core.service;

import lombok.extern.slf4j.Slf4j;
import nl.vdberg.recipesmanager.core.converter.RecipeConverter;
import nl.vdberg.recipesmanager.core.domain.Ingredients;
import nl.vdberg.recipesmanager.core.domain.Instructions;
import nl.vdberg.recipesmanager.core.domain.Recipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipe;
import nl.vdberg.recipesmanager.core.domain.json.JsonRecipeSearch;
import nl.vdberg.recipesmanager.core.exception.RecipeCreateException;
import nl.vdberg.recipesmanager.core.exception.RecipeDeleteException;
import nl.vdberg.recipesmanager.core.exception.RecipeNotFoundException;
import nl.vdberg.recipesmanager.core.exception.RecipeUpdateException;
import nl.vdberg.recipesmanager.core.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeService {

    private static final String RECIPE_NOT_FOUND = "Recipe not found ID %s";
    private static final String RECIPE_DELETION_EXCEPTION = "Recipe can not be deleted ID %s";

    private static final String RECIPE_UPDATE_EXCEPTION = "Recipe could not be updated ID %s";
    private static final String RECIPE_CREATION_EXCEPTION = "Recipe Creation has failed";
    @Resource
    RecipeRepository recipeRepository;
    @Resource
    RecipeConverter recipeConverter;
    @PersistenceContext
    private EntityManager entityManager;

    public JsonRecipe updateRecipe(long id, JsonRecipe jsonRecipe) {

        try {

            Optional<Recipe> recipe = recipeRepository.findById(id);

            if (recipe.isPresent()) {
                Recipe updatedRecipe = recipeConverter.toServer(jsonRecipe);

                updatedRecipe.setId(recipe.get().getId());

                updatedRecipe = recipeRepository.save(updatedRecipe);

                return recipeConverter.toClient(updatedRecipe);

            } else {
                log.info(RECIPE_NOT_FOUND, id);
                throw new RecipeNotFoundException(String.format(RECIPE_NOT_FOUND, id));
            }


        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new RecipeUpdateException(String.format(RECIPE_UPDATE_EXCEPTION, id));
        }

    }

    public JsonRecipe createRecipe(JsonRecipe jsonRecipe) {

        try {

            Recipe recipe = recipeConverter.toServer(jsonRecipe);

            Recipe finalRecipe = recipe;

            if (recipe.getIngredients() != null) {
                recipe.getIngredients().forEach(item -> item.setRecipe(finalRecipe));
            }
            if (recipe.getInstructions() != null) {
                recipe.getInstructions().forEach(item -> item.setRecipe(finalRecipe));
            }

            recipe = recipeRepository.save(recipe);

            log.info(String.format("Recipe saved %s", recipe.getId()));
            return recipeConverter.toClient(recipe);


        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RecipeCreateException(String.format(RECIPE_CREATION_EXCEPTION));
        }

    }

    public JsonRecipe findRecipeById(long id) {

        JsonRecipe jsonRecipe = null;
        try {
            Optional<Recipe> recipe = recipeRepository.findById(id);

            if (recipe.isPresent()) {
                jsonRecipe = recipeConverter.toClient(recipe.get());
            } else {
                log.info(String.format(RECIPE_NOT_FOUND, id));
                throw new RecipeNotFoundException(String.format(RECIPE_NOT_FOUND, id));
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new RecipeNotFoundException(String.format(RECIPE_NOT_FOUND, id));
        }

        return jsonRecipe;

    }

    public void deleteRecipe(long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isPresent()) {
            recipeRepository.delete(recipe.get());
        } else {
            log.info(String.format(RECIPE_DELETION_EXCEPTION, id));
            throw new RecipeDeleteException(String.format(RECIPE_DELETION_EXCEPTION, id));
        }
    }

    public List<JsonRecipe> findAllRecipes(JsonRecipeSearch jsonRecipeSearch) {

        List<Recipe> recipes = null;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        criteriaQuery.distinct(true);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);
        Join<Recipe, Ingredients> ingredientsJoin = recipeRoot.join("ingredients");
        Join<Recipe, Instructions> instructionsJoin = recipeRoot.join("instructions");

        List<Predicate> predicates = new ArrayList<>();

        if (jsonRecipeSearch.isVegetarian()) {
            predicates.add(criteriaBuilder.equal(recipeRoot.get("vegetarian"), jsonRecipeSearch.isVegetarian()));
        }

        if (jsonRecipeSearch.getServings() != 0) {
            predicates.add(criteriaBuilder.equal(recipeRoot.get("servingSize"), jsonRecipeSearch.getServings()));
        }

        if (jsonRecipeSearch.getExcludeSearch() != null) {
            predicates.add(criteriaBuilder.notLike((ingredientsJoin.get("name")), jsonRecipeSearch.getExcludeSearch()));
        }

        if (jsonRecipeSearch.getIncludeSearch() != null) {
            predicates.add(criteriaBuilder.like((ingredientsJoin.get("name")), jsonRecipeSearch.getIncludeSearch()));
        }

        if (jsonRecipeSearch.getSearchInstructions() != null) {
            predicates.add(criteriaBuilder.like(instructionsJoin.get("description"), jsonRecipeSearch.getSearchInstructions()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        recipes = entityManager.createQuery(criteriaQuery).getResultList();

        List<JsonRecipe> jsonRecipes = new ArrayList<>();
        jsonRecipes.addAll(recipes.stream().map(item -> recipeConverter.toClient(item)).toList());

        return jsonRecipes;
    }
}
