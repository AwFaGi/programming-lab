package commands;

import utils.CollectionManager;
import utils.CommandManager;

public class PrintAscendingCommand extends AbstractCmd{
    public PrintAscendingCommand(CommandManager commandManager){
        super(
                "print_ascending",
                "print_ascending",
                "shows elements in collection in ascending order",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().showAscending();
    };
}
