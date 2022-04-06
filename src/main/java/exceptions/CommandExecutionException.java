package exceptions;

/**
 * while running command
 */
public class CommandExecutionException extends Exception {
    public CommandExecutionException(String msg){
        super(msg);
    }
}
