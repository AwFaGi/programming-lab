package storaged;

import utils.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Human {
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
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday.format(Validator.dtFormatter) +
                '}';
    }
}