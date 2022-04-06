package commands;

import exceptions.InScriptException;
import stored.City;
import utils.CollectionManager;
import utils.CommandManager;
import utils.ScriptFileProcessor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * run commands from given script file
 */
public class ExecuteScriptCommand extends AbstractCmd{
    public ExecuteScriptCommand(CommandManager commandManager){
        super(
                "execute_script",
                "execute_script file_name",
                "add element to the collection, filling fields using newline",
                new String[]{"String"}
        );
        this.commandManager = commandManager;
    }

    @Override
    public void run(){
        String filename = (String) args.get(0);
        CommandManager.recursionDepth += 1;
        if (CommandManager.recursionDepth > CommandManager.RECURSION_LIMIT){
            throw new InScriptException("recursion limit for" + filename);
        }

        try (FileReader fr = new FileReader(filename)) {
            ScriptFileProcessor sfp = new ScriptFileProcessor(fr);
            sfp.execute();
        } catch (IOException e){
            System.out.println();
        } finally {
            CommandManager.recursionDepth -= 1;
        }
    }
}