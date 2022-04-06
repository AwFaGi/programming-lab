package commands;

import stored.City;
import utils.CollectionManager;
import utils.CommandManager;

import java.util.TreeSet;

/**
 * print elements of collection sorting by id
 */
public class ShowCommand extends AbstractCmd{
    public ShowCommand(CommandManager commandManager){
        super(
                "show",
                "show",
                "shows elements in collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
//        CollectionManager.getInstance().showByIdOrder();
        TreeSet<City> collection = CollectionManager.getInstance().getCollection();
        collection.forEach(System.out::println);
    }
}
