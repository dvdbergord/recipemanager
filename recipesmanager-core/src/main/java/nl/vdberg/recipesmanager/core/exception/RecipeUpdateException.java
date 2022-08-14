package nl.vdberg.recipesmanager.core.exception;

public class RecipeUpdateException extends RuntimeException {

    public RecipeUpdateException(final String message) {
        super(message);
    }

    public RecipeUpdateException(final String message, Exception cause) {
        super(message, cause);
    }

}
