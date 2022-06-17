package utils;

import commands.*;
import client.Client;
import client.ClientCmdManager;
import server.ServerCmdManager;
import transfer.CmdTemplate;

import java.util.ArrayList;

/**
 * fill command manager with list of available commands
 */
public class ManagerFiller {
    public static void fillCommandManager(ServerCmdManager cm){
//        cm.addCommand(new HelpCommand(cm));

        cm.addCommand(new InfoCommand(cm));
        cm.addCommand(new ShowCommand(cm));
        cm.addCommand(new PrintAscendingCommand(cm));
        cm.addCommand(new PrintDescendingCommand(cm));

        cm.addCommand(new AddCommand(cm));
        cm.addCommand(new AddIfMaxCommand(cm));
        cm.addCommand(new AddIfMinCommand(cm));
        cm.addCommand(new UpdateCommand(cm));

        cm.addCommand(new ClearCommand(cm));
        cm.addCommand(new RemoveByIdCommand(cm));
        cm.addCommand(new RemoveAllByTZCommand(cm));
        cm.addCommand(new RemoveGreaterCommand(cm));

//        cm.addCommand(new LogInCommand(cm));

//        cm.addCommand(new ExecuteScriptCommand(cm));

//        cm.addCommand(new SaveCommand(cm));
//        cm.addCommand(new ExitCommand(cm));
    }

    public static void fillCommandManager(ClientCmdManager ccm){
        for(CmdTemplate cmd: Client.hehCommands){
            ccm.addCommand(cmd);
        }
    }

    public static void fillCommandManager(ClientCmdManager ccm, ArrayList<CmdTemplate> arr){
        for(CmdTemplate cmd: arr){
            ccm.addCommand(cmd);
        }
    }
}
