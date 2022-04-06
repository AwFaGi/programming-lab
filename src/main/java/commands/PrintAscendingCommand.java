package commands;

import stored.City;
import utils.CollectionManager;
import utils.CommandManager;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * print elements in ascending order
 */
public class PrintAscendingCommand extends AbstractCmd{
    public PrintAscendingCommand(CommandManager commandManager){
        super(
                "print_ascending",
                "print_ascending",
                "shows elements in collection in ascending order",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
//        CollectionManager.getInstance().showAscending();
        TreeSet<City> collection = CollectionManager.getInstance().getCollection();
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation)
        ).forEach(System.out::println);
    }
}
