package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

/**
 * class for command 'add_if_min' (add element to the collection if 'Population' is the lowest among stored)
 */
public class AddIfMinCommand extends AbstractCmd{
    public AddIfMinCommand(ServerCmdManager commandManager){
        super(
                "add_if_min",
                "add_if_min {element}",
                "add element to the collection, filling fields using newline, " +
                        "if 'Population' is the lowest among stored",
                new String[]{"stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        City elem = (City) args.get(0);
        City minElem = CollectionManager.getInstance().getMin();

        if (minElem == null || elem.compareTo(minElem) <= 0){
            CollectionManager.getInstance().addElement(elem);
            return "Insert 1 row";
        } else {
            return "Insert 0 row";
        }
    }
}
