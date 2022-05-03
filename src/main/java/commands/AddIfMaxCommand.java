package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

import java.util.Date;

/**
 * class for command 'add_if_max' (add element to the collection if 'Population' is the greatest among stored)
 */
public class AddIfMaxCommand extends AbstractCmd{
    public AddIfMaxCommand(ServerCmdManager commandManager){
        super(
                "add_if_max",
                "add_if_max {element}",
                "add element to the collection, filling fields using newline, " +
                        "if 'Population' is the greatest among stored",
                new String[]{"stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        City elem = (City) args.get(0);
        City maxElem = CollectionManager.getInstance().getMax();

        elem.setId(CollectionManager.getInstance().generateID());
        elem.setCreationDate(new Date());

        if (maxElem == null || elem.compareTo(maxElem) >= 0){
            CollectionManager.getInstance().addElement(elem);
            return "Insert 1 row";
        } else {

            return "Insert 0 row";
        }
    }
}
