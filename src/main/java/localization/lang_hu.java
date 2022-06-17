package localization;
import java.util.ListResourceBundle;

public class lang_hu extends ListResourceBundle{
    protected Object[][] getContents() {
        return new Object[][] {
                {"return", "Visszatérés"},
                {"registration", "Bejegyzés"},
                {"register","Regisztráció"},
                {"authorisation","Engedélyezés"},
                {"sign_in","Bejönni"},
                {"registration_failed", "Sikertelen regisztráció. Próbálkozzon más adatokkal."},
                {"authorisation_failed", "A hitelesítés sikertelen. Próbálkozzon más adatokkal."},

                {"city.input_city", "Város belépés"},
                {"city.city_name", "Név"},
                {"city.coord_x", "X koordináta"},
                {"city.coord_y", "Y koordináta"},
                {"city.area", "Négyzet"},
                {"city.population", "Népesség"},
                {"city.meters_above", "Tengerszint feletti magasság"},
                {"city.timezone", "Időzóna"},
                {"city.agglomeration", "Agglomeráció"},
                {"city.climate", "Éghajlat"},
                {"city.governor", "Polgármester"},
                {"city.governor_name", "Név"},
                {"city.governor_age", "Kor"},
                {"city.governor_birthday", "Születési dátum"},
                {"city.ready", "Kész"},
                {"city.cancel", "Megszünteti"},
                {"city.error_in_field", "Mezőhiba"},

                {"main.table", "asztal"},
                {"main.creation_time", "A teremtés ideje"},
                {"main.author", "Szerző"},
                {"main.governor_name", "Polgármester neve"},
                {"main.governor_age", "Polgármester kora"},
                {"main.governor_birthday", "A polgármester születési dátuma"},
                {"main.graphics", "Grafikus kijelző"},
                {"main.management", "Ellenőrzés"},
                {"main.current_user", "Jelenlegi felhasználó: "},
                {"main.need_enter", "Be kell lépned"},
                {"main.script_ready", "A forgatókönyv elkészült"},
        };
    }
}
