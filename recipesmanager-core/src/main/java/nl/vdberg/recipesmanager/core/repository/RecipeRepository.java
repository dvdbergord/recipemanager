package nl.vdberg.recipesmanager.core.repository;

import nl.vdberg.recipesmanager.core.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
