package exceptions;

/**
 * data validation failed
 */
public class UnacceptableUserInputException extends Exception{
    public UnacceptableUserInputException(String requirements) {
        super(String.format("Available: %s. Try again.", requirements));
    }
}
