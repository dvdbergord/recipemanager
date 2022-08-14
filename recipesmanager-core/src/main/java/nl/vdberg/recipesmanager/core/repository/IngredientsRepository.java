package nl.vdberg.recipesmanager.core.repository;

import nl.vdberg.recipesmanager.core.domain.Ingredients;
import org.springframework.data.repository.CrudRepository;

public interface IngredientsRepository extends CrudRepository<Ingredients, Long> {

}
