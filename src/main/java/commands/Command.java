package commands;

//import exceptions.UnsatisfiedArgumentsException;

import exceptions.WhileRunCommandException;

public interface Command {
    void execute() throws WhileRunCommandException;
    void run();
    void sendArg(Object arg);
//    void checkArgs() throws UnsatisfiedArgumentsException;
    void clearArgs();
}
