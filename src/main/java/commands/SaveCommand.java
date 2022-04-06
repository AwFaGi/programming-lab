package commands;

import utils.CollectionManager;
import utils.CommandManager;

/**
 * save collection to the file
 */
public class SaveCommand extends AbstractCmd{
    public SaveCommand(CommandManager commandManager){
        super(
                "save",
                "save",
                "saves collection to file",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().toJSON();
    }
}