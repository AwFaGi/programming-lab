package stored;

import utils.Validator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * class for city's governor
 */
public class Human implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long age; //Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        String birthdayString = birthday == null? null : birthday.format(Validator.dtFormatter);
        return "Human {" +
                "name='" + name + "'" +
                ", age=" + age +
                ", birthday=" + birthdayString +
                "}";
    }
}