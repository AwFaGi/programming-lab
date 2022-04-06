package commands;

import utils.CollectionManager;
import utils.CommandManager;

/**
 * Remove all elements
 */
public class ClearCommand extends AbstractCmd{
    public ClearCommand(CommandManager commandManager){
        super(
                "clear",
                "clear",
                "removes all elements from collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().clearCollection();
    }
}
