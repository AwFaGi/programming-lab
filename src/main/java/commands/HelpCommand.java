package commands;

import utils.CommandManager;

public class HelpCommand extends AbstractCmd implements Command{

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
        }
    }

}
