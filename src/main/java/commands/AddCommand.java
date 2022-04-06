package commands;

import stored.City;
import utils.CollectionManager;
import utils.CommandManager;

/**
 * Class for command 'add' (add element to the collection)
 */
public class AddCommand extends AbstractCmd{
    public AddCommand(CommandManager commandManager){
        super(
                "add",
                "add {element}",
                "add element to the collection, filling fields using newline",
                new String[]{"stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        CollectionManager.getInstance().addElement( (City) args.get(0));
        System.out.println("Insert 1 row");
    }
}
