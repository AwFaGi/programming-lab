package client;

import commands.AbstractCmd;
import server.ServerCmdManager;
import transfer.CmdTemplate;

/**
 * shutdown the program
 */
public class ExitCommand extends AbstractCmd {
    private ClientCmdManager commandManager;
    public ExitCommand(){
        super(
                "exit",
                "exit",
                "shuts down the program",
                new String[]{}
        );

    }

    @Override
    public String run(){
        // TODO: 08.03.2022 check unsaved changes and ask if need to save
        System.exit(0);
        return "";
    }
}
