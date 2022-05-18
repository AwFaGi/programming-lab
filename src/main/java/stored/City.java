package stored;

import utils.Validator;

import java.io.Serializable;
import java.util.Date;

/**
 * main element to be stored in collection
 */
public class City implements Comparable<City>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private int timezone; //Значение поля должно быть больше -13, Максимальное значение поля: 15
    private Long agglomeration;
    private Climate climate; //Поле может быть null
    private Human governor; //Поле может быть null
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public Long getAgglomeration() {
        return agglomeration;
    }

    public void setAgglomeration(Long agglomeration) {
        this.agglomeration = agglomeration;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    @Override
    public int compareTo(City o) {
        return this.getPopulation().compareTo(o.getPopulation());
    }

    // TODO: 05.04.2022 create deep copies to prevent being hacked
    
    @Override
    public String toString() {
        String smth;
        if (creationDate != null){
            smth = Validator.sdFormatter.format(creationDate);
        }else{
            smth = "null";
        }

        return "City{" +
                "\n\tid=" + id +
                ",\n\tname='" + name + "'" +
                ",\n\tcoordinates=" + coordinates +
                ",\n\tcreationDate=(" + smth + ")" +
                ",\n\tarea=" + area +
                ",\n\tpopulation=" + population +
                ",\n\tmetersAboveSeaLevel=" + metersAboveSeaLevel +
                ",\n\ttimezone=" + timezone +
                ",\n\tagglomeration=" + agglomeration +
                ",\n\tclimate=" + climate +
                ",\n\tgovernor=" + governor +
                "\n} created by: " + author;
    }
}