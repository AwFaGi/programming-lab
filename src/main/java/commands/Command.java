package commands;

import exceptions.UnsatisfiedArgumentsException;

public interface Command {
    void execute() throws UnsatisfiedArgumentsException;
    void run();
    void sendArg(Object arg);
    void checkArgs() throws UnsatisfiedArgumentsException;
    void clearArgs();
}
