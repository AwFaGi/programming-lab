package commands;

import storaged.City;
import utils.CollectionManager;
import utils.CommandManager;

import java.util.Comparator;
import java.util.TreeSet;

public class PrintDescendingCommand extends AbstractCmd{
    public PrintDescendingCommand(CommandManager commandManager){
        super(
                "print_descending",
                "print_descending",
                "shows elements in collection in descending order",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
//        CollectionManager.getInstance().showDescending();
        TreeSet<City> collection = CollectionManager.getInstance().getCollection();
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea).reversed()
        ).forEach(System.out::println);
    }
}
