package localization;
import java.util.ListResourceBundle;

public class lang_ru extends ListResourceBundle{
    protected Object[][] getContents() {
        return new Object[][] {
                {"return", "Вернуться"},
                {"registration", "Регистрация"},
                {"register","Зарегистрироваться"},
                {"authorisation","Авторизация"},
                {"sign_in","Войти"},
                {"registration_failed", "Регистрация не удалась. Попробуйте другие данные."},
                {"authorisation_failed", "Авторизация не удалась. Попробуйте другие данные."},

                {"city.input_city", "Ввод города"},
                {"city.city_name", "Название"},
                {"city.coord_x", "Координата X"},
                {"city.coord_y", "Координата Y"},
                {"city.area", "Площадь"},
                {"city.population", "Население"},
                {"city.meters_above", "Высота над уровнем моря"},
                {"city.timezone", "Часовой пояс"},
                {"city.agglomeration", "Агломерация"},
                {"city.climate", "Климат"},
                {"city.governor", "Мэр"},
                {"city.governor_name", "Имя"},
                {"city.governor_age", "Возраст"},
                {"city.governor_birthday", "Дата рождения"},
                {"city.ready", "Готово"},
                {"city.cancel", "Отмена"},
                {"city.error_in_field", "Ошибка в поле"},

                {"main.table", "Таблица"},
                {"main.creation_time", "Время создания"},
                {"main.author", "Автор"},
                {"main.governor_name", "Имя мэра"},
                {"main.governor_age", "Возраст мэра"},
                {"main.governor_birthday", "Дата рождения мэра"},
                {"main.graphics", "Графическое отображение"},
                {"main.management", "Управление"},
                {"main.current_user", "Текущий пользователь: "},
                {"main.need_enter", "Необходимо ввести"},
                {"main.script_ready", "Скрипт выполнен"},

        };
    }
}
