package commands;

import exceptions.UnsatisfiedArgumentsException;
import exceptions.WhileRunCommandException;
import stored.City;
import server.ServerCmdManager;
import transfer.CmdTemplate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Mother class for all commands
 */
public abstract class AbstractCmd implements Command, ArgsDependency{
    protected final String name;
    protected final String usage;
    protected final String description;
    protected final String[] requirements;
    protected ServerCmdManager commandManager;
    protected ArrayList<Object> args = new ArrayList<>();
    protected String username;
    protected String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Constructor
     * @param name name of command
     * @param usage requirements order
     * @param description about what command does
     * @param requirements data must have
     */
    public AbstractCmd(String name, String usage, String description, String[] requirements){
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.requirements = requirements;
    }

    /**
     * Makes pre-actions and starts main body (run())
     * @throws WhileRunCommandException values of arguments are not good
     * @throws UnsatisfiedArgumentsException types of given and required arguments don't match
     */
    @Override
    public String execute() throws WhileRunCommandException, UnsatisfiedArgumentsException {
        if (args.size() != requirements.length){
            clearArgs();
            String message = requirements.length > 0? Arrays.toString(getRequirements()): "no arguments";
            throw new UnsatisfiedArgumentsException(message);
        }
        try {
            String result = run();
            clearArgs();
            return result;
        } catch (WhileRunCommandException e) {
            clearArgs();
            throw e;
        } finally {
            clearArgs();
        }

    }

    public CmdTemplate getTemplate(){
        return new CmdTemplate(getName(), getUsage(), getDescription(), getRequirements());
    }

    /**
     * gets arg and checks its type
     * @param arg argument given by user / script
     * @throws UnsatisfiedArgumentsException if argument is bad
     */
    @Override
    public void sendArg(Object arg) throws UnsatisfiedArgumentsException{
        int currentArgument = args.size();
        if (currentArgument >= requirements.length){
            String message = requirements.length > 0? Arrays.toString(getRequirements()): "no arguments";
            throw new UnsatisfiedArgumentsException(message);
        }
        // TODO: 05.04.2022 rewrite using Obj.class instead of String
        try {
            switch (requirements[currentArgument]){
                case "int":
                    Integer.parseInt( (String) arg);
                    break;
                case "float":
                    Float.parseFloat( (String) arg);
                    break;
                case "long":
                    Long.parseLong( (String) arg);
                    break;
                case "double":
                    Double.parseDouble( (String) arg);
                    break;
                case "stored.City":
                    City test = (City) arg;
                    break;
                default:
                    assert Class.forName(requirements[currentArgument]).isInstance(arg);
            }

        }catch (NumberFormatException | ClassCastException | ClassNotFoundException | ArrayIndexOutOfBoundsException | AssertionError e) {
            String message = requirements.length > 0? Arrays.toString(getRequirements()): "no arguments";
            throw new UnsatisfiedArgumentsException(message);
        }
        args.add(arg);
    }

    /**
     * count number of arguments with given class name
     * @param className class name to count number
     * @return number of arguments
     */
    public int countNeedArgType(String className){
        return (int) Arrays.stream(requirements).filter(o -> o.equals(className)).count();
    }

    public String[] getRequirements() {
        return requirements;
    }

    /**
     * clears previously added arguments
     */
    @Override
    public void clearArgs(){
        args.clear();
    }

    @Override
    public ArrayList<Object> getArgs() { return args; }

//    @Override
//    public void checkArgs() throws UnsatisfiedArgumentsException {
////        System.out.println(args);
//        if (args.size() != requirements.length){
//            String message = requirements.length > 0? Arrays.toString(getRequirements()): "no arguments";
//            throw new UnsatisfiedArgumentsException(message);
//        }
//        for (int i=0; i < requirements.length; i++){
//
//            try {
//                switch (requirements[i]){
//                    case "int":
//                        Integer.parseInt( (String) args.get(i));
//                        break;
//                    case "float":
//                        Float.parseFloat( (String) args.get(i));
//                        break;
//                    case "long":
//                        Long.parseLong( (String) args.get(i));
//                        break;
//                    case "double":
//                        Double.parseDouble( (String) args.get(i));
//                        break;
//                    case "stored.City":
//                        City test = (City) args.get(i);
//                        break;
//                    default:
//                        assert Class.forName(requirements[i]).isInstance(args.get(i));
//                }
//
//            }
//            catch (NumberFormatException | ClassCastException | ClassNotFoundException e) {
//                String message = Arrays.toString(getRequirements());
//                throw new UnsatisfiedArgumentsException(message);
//            }
//        }
//    }


    public String getName(){
        return this.name;
    }
    public String getUsage(){
        return this.usage;
    }
    public String getDescription(){
        return this.description;
    }

    public boolean isRequireArguments() {
        return requirements.length > 0;
    }

    /**
     *
     * @return information that is printed in 'help' command
     */
    public String getBigInfo(){
        return getName() + "\n\t" +
                "Usage: " + getUsage() + "\n\t" +
                "Description: " + getDescription();
    }

}
