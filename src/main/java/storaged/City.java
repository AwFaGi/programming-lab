package storaged;

import utils.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class City implements Comparable<City>{
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
        return (int) (this.getPopulation() - o.getPopulation());
    }

    @Override
    public String toString() {

        return "City{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", coordinates=" + coordinates +
                ", creationDate=(" + Validator.sdFormatter.format(creationDate) + ")" +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", timezone=" + timezone +
                ", agglomeration=" + agglomeration +
                ", climate=" + climate +
                ", governor=" + governor +
                '}';
    }
}