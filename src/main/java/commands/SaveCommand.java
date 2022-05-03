package commands;

import utils.CollectionManager;
import server.ServerCmdManager;

/**
 * save collection to the file
 */
public class SaveCommand extends AbstractCmd{
    public SaveCommand(ServerCmdManager commandManager){
        super(
                "save",
                "save",
                "saves collection to file",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        CollectionManager.getInstance().toJSON();
        return "";
    }
}