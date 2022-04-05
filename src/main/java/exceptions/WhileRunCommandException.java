package exceptions;

public class WhileRunCommandException extends RuntimeException{
    public WhileRunCommandException(String cmdName, String msg) {
        super(String.format("Error in command '%s': %s", cmdName, msg));
    }
}
