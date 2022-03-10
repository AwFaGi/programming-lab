package exceptions;

public class UnacceptableUserInputException extends Exception{
    public UnacceptableUserInputException(String requirements) {
        super(String.format("Available: %s. Try again.", requirements));
    }
}
