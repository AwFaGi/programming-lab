package commands;

import utils.CollectionManager;
import server.ServerCmdManager;

/**
 * Remove all elements
 */
public class ClearCommand extends AbstractCmd{
    public ClearCommand(ServerCmdManager commandManager){
        super(
                "clear",
                "clear",
                "removes all elements from collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        CollectionManager.getInstance().clearCollection();
        return "Successfully cleared!";
    }
}
