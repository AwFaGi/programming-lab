package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

/**
 * Class for command 'add' (add element to the collection)
 */
public class AddCommand extends AbstractCmd{
    public AddCommand(ServerCmdManager commandManager){
        super(
                "add",
                "add {element}",
                "add element to the collection, filling fields using newline",
                new String[]{"stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        CollectionManager.getInstance().addElement( (City) args.get(0));
        return "Insert 1 row";
    }
}
