package utils;

import commands.AbstractCmd;

import java.util.*;

import exceptions.NoSuchCommandException;
import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;

public class CommandManager {
    private Map<String, AbstractCmd> availableCommands = new LinkedHashMap<>();

    public ArrayList<AbstractCmd> getCommands(){
        return new ArrayList<>(availableCommands.values());
    }

    public void addCommand(AbstractCmd command){
        availableCommands.put(command.getName(), command);
    }

    public AbstractCmd getCommandByName(String name) throws NoSuchCommandException{
        if (!availableCommands.containsKey(name)) {
            throw new NoSuchCommandException(name);
        }

        return availableCommands.get(name);
    }

    public void processCommand(String userInput){
        try {
            String[] smth = userInput.split(" ", 2);
            String command = smth[0];

            AbstractCmd currentCommand = getCommandByName(command);

            // TODO: 10.03.2022 rewrite with checking on sendArg
            if (smth.length > 1){
                for (String arg : smth[1].split(" ")){
                    currentCommand.sendArg(arg);
                }
            }

            for (int i = 0; i < currentCommand.countNeedArgType("storaged.City"); i++) {
                currentCommand.sendArg(InputProcessor.inputCity());
            }

            currentCommand.execute();

        } catch (NoSuchCommandException | UnsatisfiedArgumentsException | WhileRunCommandException e) {
            System.err.println(e.getMessage());
        }
    }

}
