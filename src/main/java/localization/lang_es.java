package localization;
import java.util.ListResourceBundle;

public class lang_es extends ListResourceBundle{
    protected Object[][] getContents() {
        return new Object[][] {
                {"return", "Devolver"},
                {"registration", "Registro"},
                {"register","Registro"},
                {"authorisation","Autorización"},
                {"sign_in","Entrar"},
                {"registration_failed", "Registro fallido. Prueba con otros datos."},
                {"authorisation_failed", "Autorización fallida. Prueba con otros datos."},

                {"city.input_city", "entrada de la ciudad"},
                {"city.city_name", "Nombre"},
                {"city.coord_x", "coordenada X"},
                {"city.coord_y", "coordenada Y"},
                {"city.area", "Cuadrado"},
                {"city.population", "Población"},
                {"city.meters_above", "Altura sobre el nivel del mar"},
                {"city.timezone", "Zona horaria"},
                {"city.agglomeration", "Aglomeración"},
                {"city.climate", "Climatizado"},
                {"city.governor", "Alcalde"},
                {"city.governor_name", "Nombre"},
                {"city.governor_age", "Años"},
                {"city.governor_birthday", "Fecha de nacimiento"},
                {"city.ready", "Listo"},
                {"city.cancel", "Cancelar"},
                {"city.error_in_field", "error de campo"},

                {"main.table", "Mesa"},
                {"main.creation_time", "tiempo de creación"},
                {"main.author", "Autor"},
                {"main.governor_name", "nombre del alcalde"},
                {"main.governor_age", "edad del alcalde"},
                {"main.governor_birthday", "fecha de nacimiento del alcalde"},
                {"main.graphics", "Pantalla gráfica"},
                {"main.management", "Control"},
                {"main.current_user", "Usuario actual: "},
                {"main.need_enter", "debes entrar"},
                {"main.script_ready", "Guión completado"},

        };
    }
}
