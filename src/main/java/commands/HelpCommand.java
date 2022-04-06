package commands;

import utils.CommandManager;

/**
 * print information about all available commands
 */
public class HelpCommand extends AbstractCmd{

    public HelpCommand(CommandManager commandManager){
        super(
                "help",
                "help",
                "gives information about all available commands",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        for (AbstractCmd command:
             commandManager.getCommands()) {
            System.out.println(command.getBigInfo());
            System.out.println();
        }
    }

}
