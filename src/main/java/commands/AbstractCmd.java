package commands;

import exceptions.UnsatisfiedArgumentsException;
import storaged.City;
import utils.CommandManager;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractCmd implements Command{
    private final String name;
    private final String usage;
    private final String description;
    private final String[] requirements;
    protected CommandManager commandManager;
    protected ArrayList<Object> args = new ArrayList<>();

    public AbstractCmd(String name, String usage, String description, String[] requirements){
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.requirements = requirements;
    }

    @Override
    public void execute(){
        run();
        clearArgs();

    }
//    @Deprecated
//    @Override
//    public void sendArg(Object arg){
//        args.add(arg);
//        if (arg instanceof String){
//            String stringArg = (String) arg;
//            if (stringArg.isEmpty()) return;
//            args.addAll(
//                    Arrays.asList(
//                            stringArg.split(" ")
//                    )
//            );
//        } else {
//            args.add(arg);
//        }

//    };

    @Override
    public void sendArg(Object arg) throws UnsatisfiedArgumentsException{
        int currentArgument = args.size();
        if (currentArgument >= requirements.length){
            String message = requirements.length > 0? Arrays.toString(getRequirements()): "no arguments";
            throw new UnsatisfiedArgumentsException(message);
        }
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
                case "storaged.City":
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

    public int countNeedArgType(String className){
        return (int) Arrays.stream(requirements).filter(o -> o.equals(className)).count();
    }

    public String[] getRequirements() {
        return requirements;
    }

    @Override
    public void clearArgs(){
        args.clear();
    }

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
//                    case "storaged.City":
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

    public String getBigInfo(){
        return getName() + "\n\t" +
                "Usage: " + getUsage() + "\n\t" +
                "Description: " + getDescription();
    }

}
