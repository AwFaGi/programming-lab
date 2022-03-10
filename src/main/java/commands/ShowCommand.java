package commands;

import utils.CollectionManager;
import utils.CommandManager;

public class ShowCommand extends AbstractCmd{
    public ShowCommand(CommandManager commandManager){
        super(
                "show",
                "show",
                "shows elements in collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().showByIdOrder();
    };
}
