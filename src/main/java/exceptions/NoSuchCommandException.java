package exceptions;

/**
 * when command name is not in available commands list
 */
public class NoSuchCommandException extends Exception{
    public NoSuchCommandException(String commandName) {
        super(String.format("Couldn't resolve: '%s'. Type 'help' to see rules of work.", commandName));
    }
}
