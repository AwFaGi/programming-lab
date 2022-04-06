package commands;

import utils.CommandManager;

/**
 * shutdown the program
 */
public class ExitCommand extends AbstractCmd{
    public ExitCommand(CommandManager commandManager){
        super(
                "exit",
                "exit",
                "shuts down the program",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        // TODO: 08.03.2022 check unsaved changes and ask if need to save
        System.exit(0);
    }
}
