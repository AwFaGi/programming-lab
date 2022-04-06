package commands;

//import exceptions.UnsatisfiedArgumentsException;

import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;

/**
 * interface for working with commands and their arguments
 */
public interface Command {
    void execute() throws WhileRunCommandException, UnsatisfiedArgumentsException;
    void run();
    void sendArg(Object arg);
//    void checkArgs() throws UnsatisfiedArgumentsException;
    void clearArgs();
}
