package nl.vdberg.recipesmanager.core.exception;

public class RecipeDeleteException extends RuntimeException {

    public RecipeDeleteException(final String message) {
        super(message);
    }

    public RecipeDeleteException(final String message, Exception cause) {
        super(message, cause);
    }

}
