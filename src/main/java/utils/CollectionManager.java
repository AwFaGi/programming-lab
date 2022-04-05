package utils;

import com.google.gson.*;
import exceptions.BreachOfCollectionIntegrityException;
import exceptions.ReadWriteException;
import exceptions.WhileRunCommandException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import storaged.City;
import storaged.Climate;
import storaged.Coordinates;
import storaged.Human;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private static final CollectionManager INSTANCE = new CollectionManager();

    private TreeSet<City> collection = new TreeSet<>(
            Comparator.comparing(City::getId)
    );
    private LocalDateTime date = LocalDateTime.now();
    private String collectionName;

    public void attachFile(String filename){
        collectionName = filename;
    }

    private CollectionManager() {

    }

    public static CollectionManager getInstance() {
        return INSTANCE;
    }

    public int generateID(){
        if (collection.size() > 0) {
            City elem = collection.last();
            return elem.getId() + 1;
        }else { return 1; }
    }

    // TODO: 09.03.2022 проверка на отсутствие элемента
    public void updateElement(int id){
        
    }

    public void addElement(City city){
        collection.add(city);
    }

    @Deprecated
    public void printInfo(){
        try {
            Field stringListField = CollectionManager.class.getDeclaredField("collection");
            ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
            Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];

            System.out.println(collection.getClass());
            System.out.println(stringListClass);
            System.out.printf("Size: %d%n", collection.size());

        } catch (NoSuchFieldException e){
            System.err.println("There's an issue. Please contact me.");
        }
    }

    public String getInfo(){
        try {
            Field stringListField = CollectionManager.class.getDeclaredField("collection");
            ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
            Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];

            return "Collection: " + collection.getClass().toString() + "\n" +
                    "Element: " + stringListClass.toString() + "\n" +
                    String.format("Size: %d", collection.size()) + "\n" +
                    "Creation date: " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        } catch (NoSuchFieldException e){
            return "There's an issue. Please contact me.";
        }
    }

    public void clearCollection(){
        collection.clear();
    }
    public void removeId(int id){
        List<City> els = collection.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        collection.remove(els.get(0));
    }

    public int getSize(){
        return collection.size();
    }
    @Deprecated
    public void showByIdOrder(){
        collection.forEach(System.out::println);
    }
    @Deprecated
    public void showAscending(){
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea)
        ).forEach(System.out::println);
    }

    public TreeSet<City> getCollection(){
        return (TreeSet<City>) collection.clone();
    }
    @Deprecated
    public void showDescending(){
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea).reversed()
        ).forEach(System.out::println);
    }


    public void importJSON(){
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader(collectionName);
            JSONObject jsonObject = (JSONObject) parser.parse(fr);

            String creationDateStr = (String) jsonObject.get("creationDate");
            this.date = LocalDateTime.parse(
                    creationDateStr,
                    Validator.dtFormatter
            );

            JSONArray data = (JSONArray) jsonObject.get("data");
            Iterator<JSONObject> iterator = data.iterator();

            while (iterator.hasNext()){
                try {
                    JSONObject element = iterator.next();

                    int elementId = Integer.parseInt(
                            String.valueOf( element.get("id") )
                    );
                    if (!Validator.validateId(elementId)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "id");
                    }

                    String elementName = String.valueOf(element.get("name"));
                    if (!Validator.validateName(elementName)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "name");
                    }

                    JSONObject elementCoordinatesObj = (JSONObject) element.get("coordinates");
                    long elementCoordinateX = Long.parseLong(
                            String.valueOf( elementCoordinatesObj.get("x") )
                    );
                    if (!Validator.validateCoordinateX(elementCoordinateX)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "coordinates.x");
                    }
                    float elementCoordinateY = Float.parseFloat
                            (String.valueOf( elementCoordinatesObj.get("y") )
                    );
                    if (!Validator.validateCoordinateY(elementCoordinateY)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "coordinates.y");
                    }

                    Coordinates elementCoordinates = new Coordinates();
                    elementCoordinates.setX(elementCoordinateX);
                    elementCoordinates.setY(elementCoordinateY);

                    Date elementDate = Validator.sdFormatter.parse(
                            String.valueOf( element.get("creationDate") )
                    );

                    int elementArea = Integer.parseInt(
                            String.valueOf( element.get("area") )
                    );
                    if (!Validator.validateArea(elementArea)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "area");
                    }

                    long elementPopulation = Long.parseLong(
                            String.valueOf( element.get("population") )
                    );
                    if (!Validator.validatePopulation(elementPopulation)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "population");
                    }

                    float elementMeters = Float.parseFloat(
                            String.valueOf( element.get("metersAboveSeaLevel") )
                    );

                    int elementTZ = Integer.parseInt(
                            String.valueOf( element.get("timezone") )
                    );
                    if (!Validator.validateTimezone(elementTZ)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "timezone");
                    }

                    long elementAgglomeration = Long.parseLong(
                            String.valueOf( element.get("agglomeration") )
                    );

                    String elementClimateStr = String.valueOf(
                            element.get("climate")
                    );
                    if (!Validator.validateClimate(elementClimateStr)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "climate");
                    }
                    Climate elementClimate = null;
                    if (!elementClimateStr.equals("null")){
                        elementClimate = Climate.valueOf(elementClimateStr);
                    }

                    JSONObject elementGovernorObj = (JSONObject) element.get("governor");
                    Human elementGovernor = new Human();
                    if (String.valueOf(elementGovernorObj).equals("null")){
                        elementGovernor = null;
                    } else {
                        String governorName = String.valueOf( elementGovernorObj.get("name") );
                        if (!Validator.validateGovernorName(governorName)){
                            throw new BreachOfCollectionIntegrityException(collectionName, "governor.name");
                        }

                        long governorAge = Long.parseLong(
                                String.valueOf( elementGovernorObj.get("age") )
                        );
                        if (!Validator.validateGovernorAge(governorAge)){
                            throw new BreachOfCollectionIntegrityException(collectionName, "governor.age");
                        }

                        LocalDateTime governorBD = LocalDateTime.parse(
                                String.valueOf( elementGovernorObj.get("birthday") ),
                                Validator.dtFormatter
                        );

                        elementGovernor.setName(governorName);
                        elementGovernor.setAge(governorAge);
                        elementGovernor.setBirthday(governorBD);
                    }

                    City city = new City();
                    city.setId(elementId);
                    city.setName(elementName);
                    city.setCoordinates(elementCoordinates);
                    city.setCreationDate(elementDate);
                    city.setArea(elementArea);
                    city.setPopulation(elementPopulation);
                    city.setMetersAboveSeaLevel(elementMeters);
                    city.setTimezone(elementTZ);
                    city.setAgglomeration(elementAgglomeration);
                    city.setClimate(elementClimate);
                    city.setGovernor(elementGovernor);

                    collection.add(city);


                } catch (BreachOfCollectionIntegrityException e){
                    System.err.println(e.getMessage());
                } catch (Exception e){
                    System.err.println("Ух, что тама произошло!");
                    System.err.println("Если точнее, то:" + e.getClass().getCanonicalName());
//                    e.printStackTrace();
                }
            }
            fr.close();

        } catch (java.io.IOException | org.json.simple.parser.ParseException e){
            System.err.println("Something's wrong with given collection.\n" +
                    "So new collection has been created.");
        } catch (Exception e){
            System.err.println("Всё плохо!");
            System.err.println("Если точнее, то:" + e.getClass().getCanonicalName());
        }
    }

    public void toJSON(){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        builder.setDateFormat("dd.MM.yyyy HH:mm:ss");
        builder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(
                    LocalDateTime localDateTime,
                    Type srcType,
                    JsonSerializationContext context){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                return new JsonPrimitive(formatter.format(localDateTime));
            }
        });
        Gson gson = builder.create();
        Map<String, Object> seria = new HashMap<>();

//        System.out.println(collection.getClass());
//        System.out.println(stringListClass);
//        System.out.printf("Size: %d%n", collection.size());

        seria.put("collectionClass", collection.getClass().toGenericString());
        seria.put("size", collection.size());
        seria.put("data", collection);
        seria.put("creationDate", date);
        seria.put("lastEditDate", LocalDateTime.now());

        try {
            FileOutputStream fileStream = new FileOutputStream(collectionName);
            BufferedOutputStream bos = new BufferedOutputStream(fileStream);
            byte[] buffer = gson.toJson(seria).getBytes();
            bos.write(buffer, 0, buffer.length);
            bos.close();
            fileStream.close();

        } catch (IOException e){
            System.err.println("Something's wrong on writing");

        } catch (Exception e) {
            System.err.println("Всё плохо");
            System.err.println("Если точнее, то:" + e.getClass().getCanonicalName());
        }
    }

}
