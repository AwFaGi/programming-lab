package commands;

import storaged.City;
import utils.CollectionManager;
import utils.CommandManager;

public class AddCommand extends AbstractCmd{
    public AddCommand(CommandManager commandManager){
        super(
                "add",
                "add {element}",
                "add element to the collection, filling fields using newline",
                new String[]{"storaged.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().addElement( (City) args.get(0));
        System.out.println("Insert 1 row");
    };
}
