package client;

import commands.AbstractCmd;
import server.ServerCmdManager;
import transfer.CmdTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * print information about all available commands
 */
public class HelpCommand extends AbstractCmd {
    private ClientCmdManager commandManager;
    public HelpCommand(){
        super(
                "help",
                "help",
                "gives information about all available commands",
                new String[]{}
        );
    }

    @Override
    public String run(){
//        for (AbstractCmd command:
//             commandManager.getCommands()) {
//            System.out.println(command.getBigInfo());
//            System.out.println();
//        }
        System.out.println(
                Client.localCommands.values().stream().map(AbstractCmd::getBigInfo).collect(Collectors.joining("\n"))
                + "\n" +
                Client.hehCommands.stream().map(CmdTemplate::getBigInfo).collect(Collectors.joining("\n"))
        )
                ;
        return "";
    }

}
