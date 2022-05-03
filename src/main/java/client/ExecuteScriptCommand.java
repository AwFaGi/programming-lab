package client;

import commands.AbstractCmd;
import exceptions.InScriptException;
import transfer.CmdTemplate;
import utils.ManagerFiller;
import utils.ScriptFileProcessor;

import java.io.FileReader;
import java.io.IOException;

/**
 * run commands from given script file
 */
public class ExecuteScriptCommand extends AbstractCmd {
    public ExecuteScriptCommand(){
        super(
                "execute_script",
                "execute_script file_name",
                "add element to the collection, filling fields using newline",
                new String[]{"String"}
        );

    }

    @Override
    public String run(){
        String filename = (String) args.get(0);
        ClientCmdManager.recursionDepth += 1;
        if (ClientCmdManager.recursionDepth > ClientCmdManager.RECURSION_LIMIT){
            throw new InScriptException("recursion limit for" + filename);
        }

        try (FileReader fr = new FileReader(filename)) {
            ScriptFileProcessor sfp = new ScriptFileProcessor(fr);
            sfp.execute();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            ClientCmdManager.recursionDepth -= 1;
            this.args.clear();
        }
        return "";
    }
}