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

    public CmdTemplate(String name, String usage, String description, String[] requirements){
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.requirements = requirements;
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
