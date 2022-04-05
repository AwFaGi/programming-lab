package commands;

import exceptions.WhileRunCommandException;
import storaged.City;
import utils.CollectionManager;
import utils.CommandManager;

public class RemoveByIdCommand extends AbstractCmd{
    public RemoveByIdCommand(CommandManager commandManager){
        super(
                "remove_by_id",
                "remove_by_id id",
                "removes element by provided id",
                new String[]{"int"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
//        CollectionManager.getInstance().getSize();
        int id = Integer.parseInt( (String) args.get(0));
        if (id < 1 || id > CollectionManager.getInstance().getSize()){
            throw new WhileRunCommandException(getName(), "invalid ID");
        }

        CollectionManager.getInstance().removeId(id);

    }
}
