package utils;

import exceptions.CommandExecutionException;
import transfer.CmdTemplate;

public abstract class AbstractCmdManager<T extends CmdTemplate, P> {



    public abstract void processCommand(P cmd) throws CommandExecutionException;
}
