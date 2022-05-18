package commands;

import exceptions.WhileRunCommandException;
import utils.CollectionManager;
import server.ServerCmdManager;
import utils.Validator;

/**
 * remove all elements by provided timezone
 */
public class RemoveAllByTZCommand extends AbstractCmd{
    public RemoveAllByTZCommand(ServerCmdManager commandManager){
        super(
                "remove_all_by_timezone",
                "remove_all_by_timezone timezone",
                "removes all elements with provided timezone",
                new String[]{"int"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){

        int tz = Integer.parseInt( (String) args.get(0));
        if (!Validator.validateTimezone(tz)){
            throw new WhileRunCommandException(getName(), "invalid timezone");
        }

        int deleted = CollectionManager.getInstance().deleteAllByTimezone(tz, getUsername());

        return String.format("Rows removed: %d", deleted);

    }
}