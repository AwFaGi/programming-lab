import java.io.IOException;
import java.util.Arrays;

/**
 * Main class
 */
public class Main {
    /**
     * Entry point
     * @param args command line arguments
     * @throws IOException when can't exchange with other part
     */
    public static void main(String[] args) throws IOException {

        if(args.length == 0){
            System.err.println("You need to specify working type (server/client/client-gui)");
            System.exit(1);
        }

        if (args[0].equalsIgnoreCase("server")){
            server.Server.main(Arrays.copyOfRange(args, 1, args.length));
        } else if (args[0].equalsIgnoreCase("client")) {
            client.Client.main(Arrays.copyOfRange(args, 1, args.length));
        } else if (args[0].equalsIgnoreCase("client-gui")) {
            gui.ClientGUI.main(Arrays.copyOfRange(args, 1, args.length));
        } else {
            System.err.println("Unknown option!");
            System.exit(1);
        }

//        CommandManager cm = new CommandManager(false);
//        ManagerFiller.fillCommandManager(cm);
//
//        String filepath = System.getenv("COLLECTION");
//        CollectionManager.getInstance().attachFile(filepath);
//        CollectionManager.getInstance().importJSON();
//
//        try {
//            while (true) {
//                String command = InputProcessor.inputString();
//                try {
//                    cm.processCommand(command);
//                }catch (CommandExecutionException e){
//                    System.err.println(e.getMessage());
//                }
//            }
//        } catch (Exception e){
//            System.err.println("\nShutting down)");
//            e.printStackTrace();
//        }
    }
}
