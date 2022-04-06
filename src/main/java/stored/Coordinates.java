package stored;

/**
 * coordinates for city
 */
public class Coordinates {
    private Long x; //Поле не может быть null
    private Float y; //Максимальное значение поля: 960, Поле не может быть null

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}