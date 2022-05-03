package client;

import commands.AbstractCmd;
import exceptions.CommandExecutionException;
import exceptions.NoSuchCommandException;
import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;
import transfer.CmdTemplate;
import utils.InputProcessor;
import utils.ScriptFileProcessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User's input executor
 */
public class ClientCmdManager {
    public final static int RECURSION_LIMIT = 3;
    public static int recursionDepth = 0;

    private boolean worksInScript;
    private ScriptFileProcessor scriptFileProcessor;

    private Map<String, CmdTemplate> availableCommands = new LinkedHashMap<>();

    public ArrayList<CmdTemplate> getCommands(){
        return new ArrayList<>(availableCommands.values());
    }

    /**
     * get command class by its name
     * @param name name of command
     * @return class of command (by given name)
     * @throws NoSuchCommandException if command not found
     */
    public CmdTemplate getCommandByName(String name) throws NoSuchCommandException{
        if (!availableCommands.containsKey(name)) {
            throw new NoSuchCommandException(name);
        }
        return availableCommands.get(name);
    }

    /**
     * handle command
     * @param userInput command string (maybe with arguments)
     * @return light version of command to be sent to server
     * @throws CommandExecutionException if there was error
     */
    public CmdTemplate processCommand(String userInput) throws CommandExecutionException{
        try {
            String[] smth = userInput.split(" ", 2);
            String command = smth[0];

            CmdTemplate currentCommand = getCommandByName(command).createCopy();

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

            return currentCommand;
            //NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException
        } catch (NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException e) {
            throw new CommandExecutionException(e.getMessage());
        }
    }

    /**
     * add command to list of available commands
     * @param command command to add
     */
    public void addCommand(CmdTemplate command){
        availableCommands.put(command.name, command);
    }

    public ClientCmdManager(boolean worksInScript){
        this.worksInScript = worksInScript;
    }

    public ClientCmdManager(boolean worksInScript, ScriptFileProcessor scriptFileProcessor){
        this.worksInScript = worksInScript;
        this.scriptFileProcessor = scriptFileProcessor;
    }

    public void executeLocalCommand(String userInput) throws CommandExecutionException{
        try{
            String[] smth = userInput.split(" ", 2);
            String command = smth[0];

            AbstractCmd currentCommand = Client.localCommands.getOrDefault(command, null);

            if (currentCommand == null){
                throw new NoSuchCommandException("Can't resolve): " + command);
            }

            if (smth.length > 1){
                for (String arg : smth[1].split(" ")){
                    currentCommand.sendArg(arg);
                }
            }

            currentCommand.execute();

        } catch (NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException e){
            throw new CommandExecutionException(e.getMessage());
        }


    }
}
