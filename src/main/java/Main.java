import commands.*;
import utils.CommandManager;
import utils.InputProcessor;

public class Main {
    public static void main(String[] args) {
        CommandManager cm = new CommandManager();
        cm.addCommand(new HelpCommand(cm));
        cm.addCommand(new ExitCommand(cm));
        cm.addCommand(new InfoCommand(cm));
        cm.addCommand(new AddCommand(cm));
        cm.addCommand(new ShowCommand(cm));
        cm.addCommand(new ClearCommand(cm));
        cm.addCommand(new PrintAscendingCommand(cm));
        cm.addCommand(new PrintDescendingCommand(cm));
        cm.addCommand(new SaveCommand(cm));

        InputProcessor.enableConsoleInput();

        while(true){
            String command = InputProcessor.inputString();
            cm.processCommand(command);
        }
    }
}
