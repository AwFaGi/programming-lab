package utils;

import commands.AbstractCmd;

import java.util.*;

import exceptions.CommandExecutionException;
import exceptions.NoSuchCommandException;
import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;
import stored.City;

/**
 * class for handling commands
 */
public class CommandManager {
    public final static int RECURSION_LIMIT = 3;
    public static int recursionDepth = 0;

    private boolean worksInScript;
    private ScriptFileProcessor scriptFileProcessor;

    private Map<String, AbstractCmd> availableCommands = new LinkedHashMap<>();

    public ArrayList<AbstractCmd> getCommands(){
        return new ArrayList<>(availableCommands.values());
    }

    /**
     * add command to list of available commands
     * @param command command to add
     */
    public void addCommand(AbstractCmd command){
        availableCommands.put(command.getName(), command);
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

    public CommandManager (boolean worksInScript){
        this.worksInScript = worksInScript;
    }

    public CommandManager (boolean worksInScript, ScriptFileProcessor scriptFileProcessor){
        this.worksInScript = worksInScript;
        this.scriptFileProcessor = scriptFileProcessor;
    }

    /**
     * handle command
     * @param userInput command string (maybe with arguments)
     * @throws CommandExecutionException if there was error
     */
    public void processCommand(String userInput) throws CommandExecutionException{
        try {
            String[] smth = userInput.split(" ", 2);
            String command = smth[0];

            AbstractCmd currentCommand = getCommandByName(command);

            // TODO: 10.03.2022 rewrite with checking on sendArg (mb done?)
            if (smth.length > 1){
                for (String arg : smth[1].split(" ")){
                    currentCommand.sendArg(arg);
                }
            }

            for (int i = 0; i < currentCommand.countNeedArgType("stored.City"); i++) {
                if (!worksInScript) {
                    currentCommand.sendArg(InputProcessor.inputCity());
                } else {
                    currentCommand.sendArg(scriptFileProcessor.inputCity());
                }
            }

            currentCommand.execute();
        //NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException
        } catch (NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException e) {
            throw new CommandExecutionException(e.getMessage());
        }
    }

}
