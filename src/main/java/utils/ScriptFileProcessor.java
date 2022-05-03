package utils;

import exceptions.CommandExecutionException;
import exceptions.InScriptException;
import stored.City;
import stored.Climate;
import stored.Coordinates;
import stored.Human;
import client.ClientCmdManager;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

/**
 * manage reading commands and data in script file
 */
public class ScriptFileProcessor {
    private final Scanner sc;
    private final ClientCmdManager cm;

    public ScriptFileProcessor(FileReader fr){
        sc = new Scanner(fr);
        cm = new ClientCmdManager(true, this);
        ManagerFiller.fillCommandManager(cm);
    }

    public void execute(){
        while (sc.hasNext()){
            String cmd = sc.nextLine();
            try {
                cm.processCommand(cmd);
                System.out.println();
            } catch (CommandExecutionException e){
                System.err.println(e.getMessage());
                throw new InScriptException( String.format("failed execute '%s'", cmd) );
            }
        }

    }

    public City inputCity(){
        City city = new City();
        CollectionManager cm = CollectionManager.getInstance();

        city.setId(cm.generateID());
        city.setCreationDate(new Date());

        if (!sc.hasNextLine()){
            throw new InScriptException("failed read city.name");
        }
        city.setName(sc.nextLine()); // 1

        if (!sc.hasNextLong()){
            throw new InScriptException("failed read city.coordinates.x");
        }
        long x = sc.nextLong(); // 2
        if (!Validator.validateCoordinateX(x)){
            throw new InScriptException("unsatisfied city.coordinates.x");
        }

        if (!sc.hasNextFloat()){
            throw new InScriptException("failed read city.coordinates.y");
        }
        float y = sc.nextFloat(); // 3
        if (!Validator.validateCoordinateY(y)){
            throw new InScriptException("unsatisfied city.coordinates.y");
        }

        Coordinates c = new Coordinates();
        c.setX(x);
        c.setY(y);
        city.setCoordinates(c);

        if (!sc.hasNextInt()){
            throw new InScriptException("failed read city.area");
        }
        int area = sc.nextInt(); // 4
        if (!Validator.validateArea(area)){
            throw new InScriptException("unsatisfied city.area");
        }
        city.setArea(area);


        if (!sc.hasNextLong()){
            throw new InScriptException("failed read city.population");
        }
        long population = sc.nextLong(); // 5
        if (!Validator.validatePopulation(population)){
            throw new InScriptException("unsatisfied city.population");
        }
        city.setPopulation(population);


        if (!sc.hasNextFloat()){
            throw new InScriptException("failed read city.metersAboveSeaLevel");
        }
        float metres = sc.nextFloat(); // 6
        city.setMetersAboveSeaLevel(metres);


        if (!sc.hasNextInt()){
            throw new InScriptException("failed read city.timeZone");
        }
        int tz = sc.nextInt(); // 7
        if (!Validator.validateTimezone(tz)){
            throw new InScriptException("unsatisfied city.timeZone");
        }
        city.setTimezone(tz);


        if (!sc.hasNextLong()){
            throw new InScriptException("failed read city.agglomeration");
        }
        long agglomeration = sc.nextLong(); // 8
        city.setAgglomeration(agglomeration);

//        System.out.println(city);

        String smth = sc.nextLine();

        String climate = sc.nextLine();
//        System.out.println("climate = '" + climate);
        if (climate.equals("")){
            city.setClimate(null);
        } else {
            city.setClimate(Climate.valueOf(climate.toUpperCase()));
        }

        String governorTest = sc.nextLine();
//        System.out.println("governorTest = '" + governorTest);
        if (governorTest.equals("")) {
            city.setGovernor(null);
            return city;
        }

        Human human = new Human();
        human.setName(governorTest);

        if (!sc.hasNextLong()){
            throw new InScriptException("failed read city.governor.age");
        }
        long age = sc.nextLong();
        human.setAge(age);

        smth = sc.nextLine();
        String preDate = sc.nextLine();

        if (preDate.equals("")){
            human.setBirthday(null);
        } else {
             LocalDateTime governorBD = LocalDateTime.parse(
                    preDate,
                    Validator.dtFormatter
            );
             human.setBirthday(governorBD);
        }

        city.setGovernor(human);

        return city;
    }

}
