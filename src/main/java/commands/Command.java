package commands;

//import exceptions.UnsatisfiedArgumentsException;

public interface Command {
    void execute();
    void run();
    void sendArg(Object arg);
//    void checkArgs() throws UnsatisfiedArgumentsException;
    void clearArgs();
}
