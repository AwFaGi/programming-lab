package commands;

//import exceptions.UnsatisfiedArgumentsException;

import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;

/**
 * interface for working with commands
 */
public interface Command {
    String execute() throws WhileRunCommandException, UnsatisfiedArgumentsException;
    String run();

}
