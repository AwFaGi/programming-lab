package utils;

import exceptions.UnacceptableUserInputException;
import storaged.City;

import java.util.Scanner;

@Deprecated
public class UserAsker {
    private static Scanner scanner = new Scanner(System.in);
    public static City inputCity(){
        City city = new City();
        CollectionManager cm = CollectionManager.getInstance();

        city.setId(cm.getSize() + 1);
        city.setName("");


        return city;
    }
    public static String inputString(String prompt, boolean allowNull){
        String name;
        do {
            try {
                name = scanner.nextLine();
                if (allowNull) {
                    break;
                }
                if (!name.isEmpty()) {
                    break;
                }
                throw new UnacceptableUserInputException("not null");
            } catch (UnacceptableUserInputException e){
                System.err.println(e.getMessage());
            }
        } while (true);
        return name;
    }

}
