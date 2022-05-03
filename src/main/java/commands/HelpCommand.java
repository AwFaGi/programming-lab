package commands;

import server.ServerCmdManager;

import java.util.stream.Collectors;

/**
 * print information about all available commands
 */
public class HelpCommand extends AbstractCmd{

    public HelpCommand(ServerCmdManager commandManager){
        super(
                "help",
                "help",
                "gives information about all available commands",
                new String[]{}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run(){
//        for (AbstractCmd command:
//             commandManager.getCommands()) {
//            System.out.println(command.getBigInfo());
//            System.out.println();
//        }
        return commandManager.getCommands().stream().map(AbstractCmd::getBigInfo).collect(Collectors.joining("\n"));
    }

}
