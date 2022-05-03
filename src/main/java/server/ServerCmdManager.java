package server;

import commands.AbstractCmd;

import java.util.*;

import exceptions.CommandExecutionException;
import exceptions.NoSuchCommandException;
import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;
import transfer.CmdTemplate;

/**
 * class for handling commands
 */
public class ServerCmdManager {

    private Map<String, AbstractCmd> availableCommands = new LinkedHashMap<>();

    public ArrayList<AbstractCmd> getCommands(){
        return new ArrayList<>(availableCommands.values());
    }

    /**
     * get command class by its name
     * @param name name of command
     * @return class of command (by given name)
     * @throws NoSuchCommandException if command not found
     */
    public AbstractCmd getCommandByName(String name) throws NoSuchCommandException{
        if (!availableCommands.containsKey(name)) {
            throw new NoSuchCommandException(name);
        }
        return availableCommands.get(name);
    }

    /**
     * handle command
     * @param userInput command string (maybe with arguments)
     * @throws CommandExecutionException if there was error
     */
    public String processCommand(CmdTemplate userInput) throws CommandExecutionException{
        try {
            String[] smth = userInput.requirements;
            String command = userInput.name;

            AbstractCmd currentCommand = getCommandByName(command);

            for (Object i:
                 userInput.args) {
                currentCommand.sendArg(i);
            }
            
            return currentCommand.execute();
            //NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException
        } catch (NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException e) {
            throw new CommandExecutionException(e.getMessage());
        }
    }

    /**
     * add command to list of available commands
     * @param command command to add
     */
    public void addCommand(AbstractCmd command){
        availableCommands.put(command.getName(), command);
    }


}
