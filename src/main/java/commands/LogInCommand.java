package commands;

import server.ServerCmdManager;

public class LogInCommand extends AbstractCmd{
    public LogInCommand(ServerCmdManager commandManager){
        super(
                "login",
                "login username password",
                "authorization of user",
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
