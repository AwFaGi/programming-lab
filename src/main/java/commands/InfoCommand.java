package commands;

import utils.CollectionManager;
import server.ServerCmdManager;

/**
 * print general info about collection
 */
public class InfoCommand extends AbstractCmd{
    public InfoCommand(ServerCmdManager commandManager){
        super(
                "info",
                "info",
                "shows general information about collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        return CollectionManager.getInstance().getInfo();
    }
}
