package commands;

import utils.CollectionManager;
import utils.CommandManager;

public class PrintDescendingCommand extends AbstractCmd{
    public PrintDescendingCommand(CommandManager commandManager){
        super(
                "print_descending",
                "print_descending",
                "shows elements in collection in descending order",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().showDescending();
    };
}
