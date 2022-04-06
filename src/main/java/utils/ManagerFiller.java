package utils;

import commands.*;

/**
 * fill command manager with list of available commands
 */
public class ManagerFiller {
    public static void fillCommandManager(CommandManager cm){
        cm.addCommand(new HelpCommand(cm));

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

        cm.addCommand(new ExecuteScriptCommand(cm));

        cm.addCommand(new SaveCommand(cm));
        cm.addCommand(new ExitCommand(cm));
    }
}
