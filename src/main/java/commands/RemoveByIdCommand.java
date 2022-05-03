package commands;

import exceptions.WhileRunCommandException;
import utils.CollectionManager;
import server.ServerCmdManager;

import java.util.List;

/**
 * remove all elements by provided id
 */
public class RemoveByIdCommand extends AbstractCmd{
    public RemoveByIdCommand(ServerCmdManager commandManager){
        super(
                "remove_by_id",
                "remove_by_id id",
                "removes element by provided id",
                new String[]{"int"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){

        int id = Integer.parseInt( (String) args.get(0));

        List<Integer> ids = CollectionManager.getInstance().getAllID();

        if (!ids.contains(id)){
            throw new WhileRunCommandException(getName(), "invalid ID");
        }

        CollectionManager.getInstance().removeId(id);
        return "Rows removed: 1";

    }
}
