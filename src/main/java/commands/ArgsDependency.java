package commands;

import java.util.ArrayList;

/**
 * something with arguments
 */
public interface ArgsDependency {
    void sendArg(Object arg);
    //    void checkArgs() throws UnsatisfiedArgumentsException;
    void clearArgs();
    ArrayList<Object> getArgs();
}
