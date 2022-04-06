package utils;

import stored.Climate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * validator of fields in city
 */
public class Validator {
    final static int MIN_ID = 0;
    final static float MAX_Y = 960;
    final static int MIN_AREA = 0;
    final static Long MIN_POPULATION = 0L;
    final static int MIN_TZ = -13;
    final static int MAX_TZ = 15;
    final static Long MIN_GOVERNOR_AGE = 0L;

    public final static SimpleDateFormat sdFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static boolean validateId(int id){
        return id > MIN_ID;
    }

    public static boolean validateName(String name){
        return !name.isEmpty();
    }

    public static boolean validateCoordinateX(Long x){
        return x != null;
    }

    public static boolean validateCoordinateY(Float y){
        return y != null && y <= MAX_Y;
    }

    public static boolean validateCreationDate(Date date){
        return date != null;
    }

    public static boolean validateArea(int area){
        return area > MIN_AREA;
    }

    public static boolean validatePopulation(Long population){
        return population != null && population > MIN_POPULATION;
    }

    public static boolean validateTimezone(int tz){
        return tz >= MIN_TZ && tz <= MAX_TZ;
    }


    public static boolean validateGovernorName(String name){
        return name != null && !name.isEmpty();
    }

    public static boolean validateGovernorAge(Long age){
        return age > MIN_GOVERNOR_AGE;
    }

    public static boolean validateClimate(String s){
        if (s.equals("null") ||  s.isEmpty()){
            return true;
        }
        try{
            Climate.valueOf(s);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

}
