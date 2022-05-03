package utils;

import com.google.gson.*;
import exceptions.BreachOfCollectionIntegrityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.Server;
import stored.City;
import stored.Climate;
import stored.Coordinates;
import stored.Human;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * class for work with collection
 */
public class CollectionManager {
    private static final CollectionManager INSTANCE = new CollectionManager();
    private static final Logger LOGGER = LogManager.getLogger(CollectionManager.class);

    private TreeSet<City> collection = new TreeSet<>(
            Comparator.comparing(City::getId)
    );
    private LocalDateTime date = LocalDateTime.now();
    private String collectionName;

    /**
     * attach collection to current working session
     * @param filename file with collection
     */
    public void attachFile(String filename){
        collectionName = filename;
        LOGGER.info("Attached file: " + filename);
    }

    private CollectionManager() {}

    public static CollectionManager getInstance() {
        return INSTANCE;
    }

    /**
     * generate id for new elements
     * @return id
     */
    public int generateID(){
        if (collection.size() > 0) {
            City elem = collection.stream().max(Comparator.comparing(City::getId)).get();
            return elem.getId() + 1;
        } else { return 1; }
    }

    /**
     * add element to collection
     * @param city element to be added
     */
    public void addElement(City city){
        collection.add(city);
    }

    /**
     * print info to System.out
     */
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

    /**
     * get info about collection (stored class, collection type, creation date, size)
     * @return info about collection
     */
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

    /**
     * remove all elements
     */
    public void clearCollection(){
        collection.clear();
    }

    /**
     * remove element by specififed id
     * @param id id of element to be deleted
     */
    public void removeId(int id){
        List<City> els = collection.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        collection.remove(els.get(0));
    }

    /**
     * get number of elements in collection
     * @return size of collection
     */
    public int getSize(){
        return collection.size();
    }

    /**
     * print elements to System.out
     */
    @Deprecated
    public void showByIdOrder(){
        collection.forEach(System.out::println);
    }

    /**
     * print elements to System.out in ascending order
     */
    @Deprecated
    public void showAscending(){
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea)
        ).forEach(System.out::println);
    }

    /**
     * get all elements
     * @return treeset of elements
     */
    public TreeSet<City> getCollection(){
        return (TreeSet<City>) collection.clone();
    }

    /**
     * print elements to System.out in descending order
     */
    @Deprecated
    public void showDescending(){
        collection.stream().sorted(
                Comparator.comparing(City::getPopulation).thenComparing(City::getArea).reversed()
        ).forEach(System.out::println);
    }

    /**
     * get ids of all stored elements
     * @return list of ids
     */
    public List<Integer> getAllID(){
        return collection.stream()
                .map(City::getId)
                .collect(Collectors.toList());
    }

    /**
     * remove all elements with specified timezone
     * @param tz specified timezone
     * @return number of deleted elements
     */
    public int deleteAllByTimezone(int tz){

        List<Integer> tbd = collection.stream()
                .filter(x -> x.getTimezone() == tz)
                .map(City::getId)
                .collect(Collectors.toList());

        for (int i : tbd){
            removeId(i);
        }

        return tbd.size();
    }

    /**
     * get max element (sorted by population)
     * @return max element
     */
    public City getMax(){
        if (getSize() == 0){
            return null;
        }
        return collection.stream().max(
                Comparator.comparing(City::getPopulation)
        ).get();
    }

    /**
     * get min element (sorted by population)
     * @return min element
     */
    public City getMin(){
        if (getSize() == 0){
            return null;
        }
        return collection.stream().min(
                Comparator.comparing(City::getPopulation)
        ).get();
    }

    /**
     * update element with specified id
     * @param id of element
     * @param city new element
     */
    public void updateElement(int id, City city){
        removeId(id);
        city.setId(id);
        addElement(city);
    }

    /**
     * remove all element that are greater than given (sorted by population)
     * @param city element to be compared to
     * @return number of deleted elements
     */
    public int removeAllGreater(City city) {

        List<Integer> tbd = collection.stream()
                .filter(x -> x.compareTo(city) > 0)
                .map(City::getId)
                .collect(Collectors.toList());

        for (int i : tbd){
            removeId(i);
        }

        return tbd.size();
    }

    /**
     * read collection from file
     */
    public void importJSON(){
        JSONParser parser = new JSONParser();
        try (FileReader fr = new FileReader(collectionName) ){
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

                    Float elementMeters = null;
                    try {
                        elementMeters = Float.parseFloat(
                                String.valueOf( element.get("metersAboveSeaLevel") )
                        );
                    } catch (NumberFormatException ignored){

                    }

                    int elementTZ = Integer.parseInt(
                            String.valueOf( element.get("timezone") )
                    );
                    if (!Validator.validateTimezone(elementTZ)){
                        throw new BreachOfCollectionIntegrityException(collectionName, "timezone");
                    }

                    Long elementAgglomeration = null;
                    try{
                        elementAgglomeration = Long.parseLong(
                                String.valueOf( element.get("agglomeration") )
                        );
                    } catch (NumberFormatException ignored){

                    }


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

                        Long governorAge = null;
                        try {
                            governorAge = Long.parseLong(
                                    String.valueOf( elementGovernorObj.get("age") )
                            );
                        } catch (NumberFormatException ignored){

                        }

                        if (!Validator.validateGovernorAge(governorAge)){
                            throw new BreachOfCollectionIntegrityException(collectionName, "governor.age");
                        }

                        String governorBDString = String.valueOf( elementGovernorObj.get("birthday") );
                        LocalDateTime governorBD = null;
                        if (!governorBDString.equals("null")){
                            governorBD = LocalDateTime.parse(
                                    governorBDString,
                                    Validator.dtFormatter
                            );
                        }

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
                    LOGGER.info("City successfully loaded");
                } catch (BreachOfCollectionIntegrityException e){
                    LOGGER.error(e.getMessage());
                } catch (Exception e){
                    LOGGER.error("Ух, что тама произошло!");
                    LOGGER.error("Если точнее, то:" + e.getClass().getCanonicalName());
                    LOGGER.error(e);
                }
            }
            LOGGER.info("Collection successfully loaded");
        } catch (java.io.IOException | org.json.simple.parser.ParseException e){
            LOGGER.warn("Something's wrong with given collection.\n" +
                    "So new collection has been created.");
        } catch (NullPointerException e){
            LOGGER.fatal("You forgot to specify the collection file. I can't work with you!");
            System.exit(1);
        } catch (Exception e){
            LOGGER.fatal("Всё плохо!");
            LOGGER.fatal("Если точнее, то: " + e.getClass().getCanonicalName());
        }
    }

    /**
     * write collection to file
     */
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

        try (
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(collectionName))
        ) {
            byte[] buffer = gson.toJson(seria).getBytes();
            bos.write(buffer, 0, buffer.length);

        } catch (IOException e){
            LOGGER.error("Something's wrong on writing");

        } catch (Exception e) {
            LOGGER.error("Всё плохо");
            LOGGER.error("Если точнее, то: " + e.getClass().getCanonicalName());
        }
    }

}
