package commands;

import stored.City;
import utils.CollectionManager;
import server.ServerCmdManager;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * print elements of collection sorting by id
 */
public class ShowCommand extends AbstractCmd{
    public ShowCommand(ServerCmdManager commandManager){
        super(
                "show",
                "show",
                "shows elements in collection",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
//        CollectionManager.getInstance().showByIdOrder();
//        TreeSet<City> collection = CollectionManager.getInstance().getCollection();
//        collection.forEach(System.out::println);

        return CollectionManager.getInstance().getCollection().stream().sorted(
                    Comparator.comparing(City::getName)
                )
                .map(City::toString).collect(Collectors.joining("\n"));
    }
}
