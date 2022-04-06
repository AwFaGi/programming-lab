import exceptions.CommandExecutionException;
import utils.CollectionManager;
import utils.CommandManager;
import utils.InputProcessor;
import utils.ManagerFiller;

/**
 * Main class
 */
public class Main {
    /**
     * Entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {

        CommandManager cm = new CommandManager(false);
        ManagerFiller.fillCommandManager(cm);

        String filepath = System.getenv("COLLECTION");
        CollectionManager.getInstance().attachFile(filepath);
        CollectionManager.getInstance().importJSON();

        try {
            while (true) {
                String command = InputProcessor.inputString();
                try {
                    cm.processCommand(command);
                }catch (CommandExecutionException e){
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e){
            System.err.println("\nShutting down)");
            e.printStackTrace();
        }
    }
}
