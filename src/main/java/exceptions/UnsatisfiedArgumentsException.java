package exceptions;

/**
 * bad command arguments
 */
public class UnsatisfiedArgumentsException extends RuntimeException{
    public UnsatisfiedArgumentsException(String requirements) {
        super(String.format("Incompatible arguments. Required %s", requirements));
    }
}
