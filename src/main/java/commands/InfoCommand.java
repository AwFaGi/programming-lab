package commands;

import utils.CollectionManager;
import utils.CommandManager;

/**
 * print general info about collection
 */
public class InfoCommand extends AbstractCmd{
    public InfoCommand(CommandManager commandManager){
        super(
                "info",
                "info",
                "shows general information about collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        System.out.println(CollectionManager.getInstance().getInfo());
    }
}
