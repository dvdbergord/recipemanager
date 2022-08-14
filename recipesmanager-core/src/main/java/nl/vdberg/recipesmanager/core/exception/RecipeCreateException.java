package nl.vdberg.recipesmanager.core.exception;

public class RecipeCreateException extends RuntimeException {

    public RecipeCreateException(final String message) {
        super(message);
    }

    public RecipeCreateException(final String message, Exception cause) {
        super(message, cause);
    }

}
