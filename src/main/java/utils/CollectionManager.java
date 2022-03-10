package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import storaged.City;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class CollectionManager {
    private static final CollectionManager INSTANCE = new CollectionManager();

    private TreeSet<City> collection = new TreeSet<>(
            Comparator.comparing(City::getId)
    );
    private Date date = new Date();

    private CollectionManager() {
    }

    public static CollectionManager getInstance() {
        return INSTANCE;
    }

    // TODO 08.03.2022 проверка валидности city при чтении...
    public void loadCollection(String filename){

    };

    // TODO: 09.03.2022 проверка на отсутствие элемента
    public void updateElement(int id){
        
    }

    // TODO: 09.03.2022 проверка на существование подобного жлемента по name
    public void addElement(City city){
        collection.add(city);
    };

    public void printInfo() throws NoSuchFieldException{
        Field stringListField = CollectionManager.class.getDeclaredField("collection");
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];

        System.out.println(collection.getClass());
        System.out.println(stringListClass);
        System.out.printf("Size: %d%n", collection.size());
    }

    public void clearCollection(){
        collection.clear();
    }

    public int getSize(){
        return collection.size();
    };

    public void showByIdOrder(){
        collection.stream().forEach(System.out::println);
    }
    public void showAscending(){
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea)
        ).forEach(System.out::println);
    }
    public void showDescending(){
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea).reversed()
        ).forEach(System.out::println);
    }

    public void toJSON(){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Map<String, Object> seria = new HashMap<>();

//        System.out.println(collection.getClass());
//        System.out.println(stringListClass);
//        System.out.printf("Size: %d%n", collection.size());

        seria.put("collectionClass", collection.getClass().toGenericString());
        seria.put("size", collection.size());
        seria.put("data", collection);
        System.out.println(gson.toJson(seria));
        File smth = new File("output.json");
        try {
            smth.createNewFile();
            FileWriter writer = new FileWriter(smth);
            writer.write(gson.toJson(seria));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
