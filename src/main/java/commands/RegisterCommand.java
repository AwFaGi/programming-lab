package commands;

import server.ServerCmdManager;

public class RegisterCommand extends AbstractCmd{
    public RegisterCommand(ServerCmdManager commandManager){
        super(
                "register",
                "register username password",
                "registration of new user",
                new String[]{"String", "String"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public String run() {
        System.out.println("It works!");
        return null;
    }
}
