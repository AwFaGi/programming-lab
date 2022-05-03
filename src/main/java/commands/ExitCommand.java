package commands;

import server.ServerCmdManager;

/**
 * shutdown the program
 */
public class ExitCommand extends AbstractCmd{
    public ExitCommand(ServerCmdManager commandManager){
        super(
                "exit",
                "exit",
                "shuts down the program",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        // TODO: 08.03.2022 check unsaved changes and ask if need to save
        System.exit(0);
        return "";
    }
}
