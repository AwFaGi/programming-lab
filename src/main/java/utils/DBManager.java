package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Server;
import stored.City;
import stored.Climate;
import stored.Coordinates;
import stored.Human;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

public class DBManager {
    private static final Logger LOGGER = LogManager.getLogger(DBManager.class);
    private static final DBManager INSTANCE = new DBManager();
    private final String connectionString = "jdbc:postgresql://pg/studs";
    private final String forTestsConnectionString = "jdbc:postgresql://localhost:9000/studs";
    private final String name = System.getenv("DB_USERNAME");
    private final String pass = System.getenv("DB_PASSWORD");
    private Connection connection;

    private final String insertString = "";

    public static DBManager getInstance(){
        return INSTANCE;
    }

    private DBManager() {
        try {
            String checkPlace = System.getenv("WORKING_PLACE");
            String finalConnectionString;
            if ("windows".equals(checkPlace)){
                finalConnectionString = forTestsConnectionString;
            }else {
                finalConnectionString = connectionString;
            }
            connection = DriverManager.getConnection(
                    finalConnectionString,
                    name,
                    pass
            );
            LOGGER.info("Sql connection created!");
            initializeUsers();
            initializeCities();
            LOGGER.info("Database's been initialized!");
        }catch (SQLException e){
            LOGGER.fatal("Sql error");
            System.exit(-1);
        }

    }

    public synchronized void remove(City city) {
        try (PreparedStatement sm = connection.prepareStatement("DELETE FROM city_catalog WHERE id = ?")){
            sm.setInt(1, city.getId());
            sm.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }

    public synchronized void clear(String username){
        try (PreparedStatement sm = connection.prepareStatement("DELETE FROM users WHERE username=?")){
            sm.setString(1, username);
            sm.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }

    public synchronized boolean register(String username, String password){
        try (PreparedStatement sm = connection.prepareStatement("SELECT * from users WHERE username=?");
                PreparedStatement prsm = connection.prepareStatement("INSERT INTO users\n" +
                        "(id, username, password)\n" +
                        "VALUES (\n" +
                        "    (SELECT nextval('user_id')), ?, ?\n" +
                        ");"))
        {
            sm.setString(1, username);
            ResultSet resultSet = sm.executeQuery();
            if (resultSet.next()){
                return false;
            }
            prsm.setString(1, username);
            prsm.setString(2, Converter.computeMD5hash(password));
            prsm.execute();
            return true;

        }catch (SQLException | NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public synchronized boolean login(String username, String password){
        try (PreparedStatement sm = connection.prepareStatement("SELECT * from users WHERE username=? AND password=?"))
        {
            sm.setString(1, username);
            sm.setString(2, Converter.computeMD5hash(password));

            ResultSet resultSet = sm.executeQuery();
            return resultSet.next();

        }catch (SQLException | NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    private void initializeUsers() throws SQLException {
        try(Statement sm = connection.createStatement();) {

            sm.execute("CREATE TABLE IF NOT EXISTS users\n" +
                    "(\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    username VARCHAR UNIQUE,\n" +
                    "    password VARCHAR(32)\n" +
                    "                                 );\n" +
                    "\n" +
                    "CREATE SEQUENCE IF NOT EXISTS user_id START 1 MINVALUE 1 MAXVALUE 2147483647;");
            sm.execute("CREATE SEQUENCE IF NOT EXISTS user_id START 1 MINVALUE 1 MAXVALUE 2147483647;");

        }
    }

    private void initializeCities() throws SQLException{
        try(Statement sm = connection.createStatement();) {

            sm.execute("CREATE TABLE IF NOT EXISTS city_catalog\n" +
                    "(\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    name VARCHAR NOT NULL,\n" +
                    "    coordinates_x BIGINT NOT NULL ,\n" +
                    "    coordinates_y REAL NOT NULL ,\n" +
                    "    creation_date DATE NOT NULL ,\n" +
                    "    area INTEGER CHECK ( area>0 ),\n" +
                    "    population BIGINT NOT NULL CHECK ( population>0 ),\n" +
                    "    meters_above_sea_level REAL,\n" +
                    "    timezone INTEGER CHECK ( timezone>=-13 AND timezone<=15),\n" +
                    "    agglomeration BIGINT,\n" +
                    "    climate VARCHAR,\n" +
                    "    governor_name VARCHAR,\n" +
                    "    governor_age BIGINT CHECK ( governor_age>0 ),\n" +
                    "    governor_birthday DATE,\n" +
                    "    author VARCHAR\n" +
                    "\n" +
                    ");");
            sm.execute("CREATE SEQUENCE IF NOT EXISTS city_id START 1 MINVALUE 1 MAXVALUE 2147483647;");

        }
    }

    public synchronized TreeSet<City> getAllByAuthor(String authorName) throws SQLException{
        TreeSet<City> result = new TreeSet<>(
                Comparator.comparing(City::getId)
        );
        try (PreparedStatement sm = connection.prepareStatement("SELECT * FROM city_catalog WHERE author=?;")){
            sm.setString(1, authorName);
            boolean hasResult =  sm.execute();

            ResultSet resultSet;
            if (hasResult){
                resultSet = sm.getResultSet();
                while (true){
                    hasResult = resultSet.next();
                    if (!hasResult){
                        break;
                    }
                    City elem = new City();
                    elem.setId(resultSet.getInt("id"));
                    elem.setName(resultSet.getString("name"));
                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(resultSet.getLong("coordinates_x"));
                    coordinates.setY(resultSet.getFloat("coordinates_y"));
                    elem.setCoordinates(coordinates);
                    Timestamp creationDate = resultSet.getTimestamp("creation_date");
                    elem.setCreationDate(new Date(creationDate.getTime()));
                    elem.setArea(resultSet.getInt("area"));
                    elem.setPopulation(resultSet.getLong("population"));
                    elem.setMetersAboveSeaLevel(resultSet.getFloat("meters_above_sea_level"));
                    elem.setTimezone(resultSet.getInt("timezone"));
                    String climate = resultSet.getString("climate");
                    if (resultSet.wasNull()){
                        elem.setClimate(null);
                    }else{
                        elem.setClimate(Climate.valueOf(climate));
                    }
                    String governorName = resultSet.getString("governor_name");
                    if (resultSet.wasNull()){
                        elem.setGovernor(null);
                    }else{
                        Human governor = new Human();
                        governor.setName(governorName);
                        governor.setAge(resultSet.getLong("governor_age"));
                        Timestamp governorBD = resultSet.getTimestamp("governor_birthday");
                        if (resultSet.wasNull()){
                            governor.setBirthday(null);
                        }else {
                            governor.setBirthday(governorBD.toLocalDateTime());
                        }
                        elem.setGovernor(governor);
                    }

                    result.add(elem);

                }
            }
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public synchronized TreeSet<City> getAll(){
        TreeSet<City> result = new TreeSet<>(
                Comparator.comparing(City::getId)
        );
        try (PreparedStatement sm = connection.prepareStatement("SELECT * FROM city_catalog;")){

            ResultSet resultSet = sm.executeQuery();
            while (resultSet.next()){
                City elem = new City();
                elem.setId(resultSet.getInt("id"));
                elem.setName(resultSet.getString("name"));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong("coordinates_x"));
                coordinates.setY(resultSet.getFloat("coordinates_y"));
                elem.setCoordinates(coordinates);
                Timestamp creationDate = resultSet.getTimestamp("creation_date");
                elem.setCreationDate(new Date(creationDate.getTime()));
                elem.setArea(resultSet.getInt("area"));
                elem.setPopulation(resultSet.getLong("population"));
                elem.setMetersAboveSeaLevel(resultSet.getFloat("meters_above_sea_level"));
                elem.setTimezone(resultSet.getInt("timezone"));
                String climate = resultSet.getString("climate");
                if (resultSet.wasNull()){
                    elem.setClimate(null);
                }else{
                    elem.setClimate(Climate.valueOf(climate));
                }
                String governorName = resultSet.getString("governor_name");
                if (resultSet.wasNull()){
                    elem.setGovernor(null);
                }else{
                    Human governor = new Human();
                    governor.setName(governorName);
                    governor.setAge(resultSet.getLong("governor_age"));
                    Timestamp governorBD = resultSet.getTimestamp("governor_birthday");
                    if (resultSet.wasNull()){
                        governor.setBirthday(null);
                    }else {
                        governor.setBirthday(governorBD.toLocalDateTime());
                    }
                    elem.setGovernor(governor);
                }
                elem.setAuthor(resultSet.getString("author"));

                result.add(elem);
            }

        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public synchronized String insertCity(City elem) {

        try(PreparedStatement sm = connection.prepareStatement("INSERT INTO city_catalog (id, name, coordinates_x, coordinates_y, creation_date, area, population, meters_above_sea_level, timezone, agglomeration, climate, governor_name, governor_age, governor_birthday, author)\n" +
                "VALUES ((SELECT nextval('city_id')), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n")){
            sm.setString(1, elem.getName());
            sm.setLong(2, elem.getCoordinates().getX());
            sm.setFloat(3, elem.getCoordinates().getY());
            sm.setTimestamp(4, new Timestamp(elem.getCreationDate().getTime()));
            sm.setInt(5, elem.getArea());
            sm.setLong(6, elem.getPopulation());
            sm.setFloat(7, elem.getMetersAboveSeaLevel());
            sm.setInt(8, elem.getTimezone());
            sm.setLong(9, elem.getAgglomeration());
            if (elem.getClimate() == null){
                sm.setNull(10, Types.NULL);
            }else {
                sm.setString(10, elem.getClimate().toString());
            }
            System.out.println(sm);
            System.out.println(elem.getGovernor());
            if (elem.getGovernor() == null){
                sm.setNull(11, Types.NULL);
                sm.setNull(12, Types.NULL);
                sm.setNull(13, Types.NULL);
            }else {
                sm.setString(11, elem.getGovernor().getName());
                sm.setLong(12, elem.getGovernor().getAge());
                sm.setTimestamp(13, Timestamp.valueOf(elem.getGovernor().getBirthday()));
            }
            sm.setString(14, elem.getAuthor());
            System.out.println(sm);
            sm.execute();
            return "1 row inserted";
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
            LOGGER.fatal(e.getStackTrace());
            return "0 rows inserted";
        }

    }

    public synchronized String forceUpdate(int id, City elem) {
        String res;
        try(PreparedStatement sm = connection.prepareStatement("UPDATE city_catalog SET (name, coordinates_x, coordinates_y, creation_date, area, population, meters_above_sea_level, timezone, agglomeration, climate, governor_name, governor_age, governor_birthday)\n" +
                "= (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?;\n")){
            sm.setString(1, elem.getName());
            sm.setLong(2, elem.getCoordinates().getX());
            sm.setFloat(3, elem.getCoordinates().getY());
            sm.setTimestamp(4, new Timestamp(elem.getCreationDate().getTime()));
            sm.setInt(5, elem.getArea());
            sm.setLong(6, elem.getPopulation());
            sm.setFloat(7, elem.getMetersAboveSeaLevel());
            sm.setInt(8, elem.getTimezone());
            sm.setLong(9, elem.getAgglomeration());
            if (elem.getClimate() == null){
                sm.setNull(10, Types.NULL);
            }else {
                sm.setString(10, elem.getClimate().toString());
            }
            if (elem.getGovernor() == null){
                sm.setNull(11, Types.NULL);
                sm.setNull(12, Types.NULL);
                sm.setNull(13, Types.NULL);
            }else {
                sm.setString(11, elem.getGovernor().getName());
                sm.setLong(12, elem.getGovernor().getAge());
                sm.setTimestamp(13, Timestamp.valueOf(elem.getGovernor().getBirthday()));
            }
            sm.setInt(14, id);

            res = sm.execute()? "1 row updated": "0 rows updated";
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
            return "0 rows updated";
        }
        return res;
    }
}
