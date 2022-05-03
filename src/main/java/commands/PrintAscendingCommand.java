package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * print elements in ascending order
 */
public class PrintAscendingCommand extends AbstractCmd{
    public PrintAscendingCommand(ServerCmdManager commandManager){
        super(
                "print_ascending",
                "print_ascending",
                "shows elements in collection in ascending order",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
//        CollectionManager.getInstance().showAscending();
//        TreeSet<City> collection = CollectionManager.getInstance().getCollection();
//        collection.stream().sorted(
//                Comparator.comparing(City::getPopulation)
//        ).forEach(System.out::println);

        return CollectionManager.getInstance().getCollection().stream()
                .sorted(
                    Comparator.comparing(City::getPopulation)
                ).map(City::toString).collect(Collectors.joining("\n"));

    }
}
