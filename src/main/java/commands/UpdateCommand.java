package commands;

import exceptions.WhileRunCommandException;
import stored.City;
import utils.CollectionManager;
import utils.CommandManager;

import java.util.List;

/**
 * update element in collection by given id
 */
public class UpdateCommand extends AbstractCmd{
    public UpdateCommand(CommandManager commandManager){
        super(
                "update",
                "update id {element}",
                "update element with given id, filling fields using newline",
                new String[]{"int", "stored.City"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        int id = Integer.parseInt( (String) args.get(0));
        City elem = (City) args.get(1);

        List<Integer> ids = CollectionManager.getInstance().getAllID();

        if (!ids.contains(id)){
            throw new WhileRunCommandException(getName(), "invalid ID");
        }

        CollectionManager.getInstance().updateElement(id, elem);
        System.out.println("Updated");

    }
}