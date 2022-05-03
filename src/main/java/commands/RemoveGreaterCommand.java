package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

/**
 * remove all elements greater than provided element
 */
public class RemoveGreaterCommand extends AbstractCmd{
    public RemoveGreaterCommand(ServerCmdManager commandManager){
        super(
                "remove_greater",
                "remove_greater {element}",
                "removes all elements that are greater than specified",
                new String[]{"stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        City elem = (City) args.get(0);

        int deleted = CollectionManager.getInstance().removeAllGreater(elem);
        return String.format("Rows removed: %d", deleted);

    }
}
