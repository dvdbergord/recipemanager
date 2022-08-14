package nl.vdberg.recipesmanager.core.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(final String message) {
        super(message);
    }

    public RecipeNotFoundException(final String message, Exception cause) {
        super(message, cause);
    }

}
