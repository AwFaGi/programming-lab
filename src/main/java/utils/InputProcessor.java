package utils;

import client.Client;
import exceptions.UnacceptableUserInputException;
import stored.City;
import stored.Climate;
import stored.Coordinates;
import stored.Human;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * class with static element to read objects from console
 */
public class InputProcessor{
    private final static Scanner scanner = new Scanner(System.in);
    private static final String INPUT_PREFIX = ">>> ";

    public static City inputCity(){
        //todo rewrite using validator
        City city = new City();
//        CollectionManager cm = CollectionManager.getInstance();

//        city.setId(cm.generateID());
//        city.setCreationDate(new Date());

        city.setName(inputString("Enter name (String, not null)", false));
        city.setCoordinates(inputCoordinates());

        city.setArea(inputInt("Enter area (int, >0)", 0));
        city.setPopulation(inputLong("Enter population (Long, not null, >0)", false, 0L));
        city.setMetersAboveSeaLevel(
                inputFloat("Enter meters above sea level (Float)", true)
        );
        city.setTimezone(inputInt("Enter time zone (int, min: -13, max: 15)", -13, 15));
        city.setAgglomeration(inputLong("Enter agglomeration (Long)", true));
        city.setClimate(inputClimate());
        city.setGovernor(inputHuman());
        city.setAuthor(Client.login);

        return city;
    }


    public static Coordinates inputCoordinates(){
        Coordinates coordinates = new Coordinates();
        coordinates.setX(inputLong("Enter X coordinate (Long, not null)", false));
        coordinates.setY(
                inputFloat("Enter Y coordinate (Float, max: 960, not null)", false, 960F)
        );
        return coordinates;
    }

    public static Human inputHuman(){
        do {
            try {
                System.out.println("Want to input governor? [Yes/No]");
                System.out.print(INPUT_PREFIX);
                String need = scanner.nextLine().toLowerCase();
                if (need.equals("no")){
                    return null;
                }
                if (need.equals("yes")){
                    break;
                }
                throw new UnacceptableUserInputException("'Yes' or 'No'");

            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        }while (true);

        Human human = new Human();
        human.setName(inputString("Enter governor's name", false));
        human.setAge(inputLong("Enter governor's age (Long, >0)", true, 0L));
        human.setBirthday(getLocalDateTime());
        return human;
    }

    public static LocalDateTime getLocalDateTime(){
        do {
            try {
                System.out.println("Enter governor's birthday ('DD.MM.YYYY HH:MM:SS')");
                System.out.print(INPUT_PREFIX);
                try {
                    String value = scanner.nextLine();
                    // TODO: 05.04.2022 check ability of work
                    if (value.isEmpty()){
                        return null;
                    }
                    return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                } catch (IllegalArgumentException | DateTimeParseException e){
                    throw new UnacceptableUserInputException("datetime in defined format");
                }
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    public static String inputString(String prompt, boolean allowNull){
        String value;
        System.out.println(prompt);
        do {

            System.out.print(INPUT_PREFIX);
            try {
                value = scanner.nextLine();
                if (allowNull) {
                    return value;
                }
                if (!value.isEmpty()) {
                    return value;
                }
                throw new UnacceptableUserInputException("not null");
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    public static String inputString(){
        System.out.print(INPUT_PREFIX);
        return scanner.nextLine();
    }

    public static int inputInt(String prompt, int min_value, int max_value) {
        int value;
        String cash;
        do {
            System.out.println(prompt);
            System.out.print(INPUT_PREFIX);
//            scanner.next();
            try {
                cash = scanner.nextLine();

                if (cash.isEmpty()) {
                    throw new UnacceptableUserInputException("int");
                }
                try {
                    value = Integer.parseInt(cash);
                } catch (ClassCastException | NumberFormatException e) {
                    throw new UnacceptableUserInputException("int");
                }
                if (value < min_value || value > max_value) {
                    throw new UnacceptableUserInputException(
                            String.format("int in range [%d..%d])", min_value, max_value)
                    );
                }
                return value;
            } catch (UnacceptableUserInputException e) {
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    public static int inputInt(String prompt, int min_value){
        int value;
        String cash;
        do {
            System.out.println(prompt);
            System.out.print(INPUT_PREFIX);
//            scanner.next();
            try {
                cash = scanner.nextLine();

                if (cash.isEmpty()) {
                    throw new UnacceptableUserInputException("int");
                }
                try {
                    value = Integer.parseInt(cash);
                } catch (ClassCastException | NumberFormatException e){
                    throw new UnacceptableUserInputException("int");
                }
                if (value <= min_value){
                    throw new UnacceptableUserInputException(
                            String.format("int in range (%d..)", min_value)
                    );
                }
                return value;
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    public static Long inputLong(String prompt, boolean allowNull){
        Long value;
        String cash;
        do {
            System.out.println(prompt);
            System.out.print(INPUT_PREFIX);
            try {
                cash = scanner.nextLine();
                if (cash.isEmpty() && allowNull) {
                    return null;
                }
                if (cash.isEmpty()) {
                    throw new UnacceptableUserInputException("not null Long");
                }
                try {
                    return new Long(cash);
                } catch (Exception e){
                    throw new UnacceptableUserInputException("Long");
                }
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);

    }

    public static Long inputLong(String prompt, boolean allowNull, Long min_value){
        Long value;
//        String cash;
        do {
//            System.out.println(prompt);
//            System.out.print(INPUT_PREFIX);
            try {
                value = inputLong(prompt, allowNull);
                if (value == null){
                    return null;
                }
                if (value.compareTo(min_value) <= 0){
                    throw new UnacceptableUserInputException(
                            String.format("Long in range (%d..)", min_value)
                    );
                }
                return value;
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);

    }

    public static Float inputFloat(String prompt, boolean allowNull){
        Float value;
        String cash;
        do {
            System.out.println(prompt);
            System.out.print(INPUT_PREFIX);
            try {
                cash = scanner.nextLine();
                if (cash.isEmpty() && allowNull) {
                    return null;
                }
                if (cash.isEmpty()) {
                    throw new UnacceptableUserInputException("not null Float");
                }
                try {
                    return new Float(cash);
                } catch (Exception e){
                    throw new UnacceptableUserInputException("Float");
                }
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);

    }

    public static Float inputFloat(String prompt, boolean allowNull, Float max_value){
        Float value;
//        String cash;
        do {
//            System.out.println(prompt);
//            System.out.print(INPUT_PREFIX);
            try {
                value = inputFloat(prompt, allowNull);
                if (value.compareTo(max_value) > 0){
                    throw new UnacceptableUserInputException(
                            String.format("Float in range (..%f)", max_value)
                    );
                }
                return value;
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);

    }

    public static Climate inputClimate(){
        String value;
        do {
            System.out.println("Enter Climate");
            System.out.println(Arrays.asList(Climate.values()));
            System.out.print(INPUT_PREFIX);
            try {
                value = scanner.nextLine().toUpperCase();
                if (value.isEmpty()) return null;
                try {
                     return Climate.valueOf(value);
                } catch (IllegalArgumentException e) {
                    throw new UnacceptableUserInputException("one of Climate values or null");
                }
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);
    }

}
