package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * print elements in descending order
 */
public class PrintDescendingCommand extends AbstractCmd{
    public PrintDescendingCommand(ServerCmdManager commandManager){
        super(
                "print_descending",
                "print_descending",
                "shows elements in collection in descending order",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
//        CollectionManager.getInstance().showDescending();
//        TreeSet<City> collection = CollectionManager.getInstance().getCollection();
//        collection.stream().sorted(
//                Comparator.comparing(City::getPopulation).reversed()
//        ).forEach(System.out::println);

        return CollectionManager.getInstance().getCollection().stream()
                .sorted(
                        Comparator.comparing(City::getPopulation).reversed()
                ).map(City::toString).collect(Collectors.joining("\n"));
    }
}
