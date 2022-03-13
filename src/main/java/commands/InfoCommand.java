package commands;

import utils.CollectionManager;
import utils.CommandManager;

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
        try {
            CollectionManager.getInstance().printInfo();
        } catch (NoSuchFieldException e){
            System.err.println("There's an issue. Please contact me.");
        }
    }
}
