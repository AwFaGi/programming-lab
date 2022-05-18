package transfer;

import commands.ArgsDependency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * light version of command to transfer
 */
public class CmdTemplate implements Serializable, ArgsDependency {
    public final String name;
    public final String usage;
    public final String description;
    public final String[] requirements;
    public ArrayList<Object> args = new ArrayList<>();
    private String username;
    private String password;

    public void updateAuth(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public CmdTemplate(String name, String username, String password){
        this.name = name;
        this.usage = null;
        this.description = null;
        this.requirements = null;
        this.username = username;
        this.password = password;
    }

    public CmdTemplate(String name, String usage, String description, String[] requirements){
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.requirements = requirements;
        this.username = null;
        this.password = null;
    }

    public CmdTemplate(String name, String usage, String description, String[] requirements, String username, String password){
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.requirements = requirements;
        this.username = username;
        this.password = password;
    }

    @Override
    public ArrayList<Object> getArgs() {
        return args;
    }

    @Override
    public void sendArg(Object e) {
        args.add(e);
    }

    @Override
    public void clearArgs() { args.clear(); }

    public int countNeedArgType(String className){
        return (int) Arrays.stream(requirements).filter(o -> o.equals(className)).count();
    }

    public CmdTemplate createCopy(){
        return new CmdTemplate(name, usage, description, requirements);
    }

    public String getBigInfo(){
        return name + "\n\t" +
                "Usage: " + usage + "\n\t" +
                "Description: " + description;
    }

    @Override
    public String toString() {
        return "CmdTemplate{" +
                "name='" + name + '\'' +
                ", usage='" + usage + '\'' +
                ", description='" + description + '\'' +
                ", requirements=" + Arrays.toString(requirements) +
                ", args=" + args +
                '}';
    }
}
