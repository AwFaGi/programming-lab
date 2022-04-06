package exceptions;

/**
 * when loading collection
 */
public class BreachOfCollectionIntegrityException extends RuntimeException{
    public BreachOfCollectionIntegrityException(String name, String part) {
        super(String.format("Something is not good while parsing '%s'. It's in '%s'", name, part));
    }
}
