package commands;

import exceptions.WhileRunCommandException;
import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

import java.util.Date;
import java.util.List;

/**
 * update element in collection by given id
 */
public class UpdateCommand extends AbstractCmd{
    public UpdateCommand(ServerCmdManager commandManager){
        super(
                "update",
                "update id {element}",
                "update element with given id, filling fields using newline",
                new String[]{"int", "stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
        int id = Integer.parseInt( (String) args.get(0));
        City elem = (City) args.get(1);

        elem.setCreationDate(new Date());

        List<Integer> ids = CollectionManager.getInstance().getAllID();

        if (!ids.contains(id)){
            throw new WhileRunCommandException(getName(), "invalid ID");
        }

        CollectionManager.getInstance().updateElement(id, elem);
        return "Updated";

    }
}