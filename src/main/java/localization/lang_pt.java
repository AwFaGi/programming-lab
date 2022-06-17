package localization;
import java.util.ListResourceBundle;

public class lang_pt extends ListResourceBundle{
    protected Object[][] getContents() {
        return new Object[][] {
                {"return", "Retornar"},
                {"registration", "Cadastro"},
                {"register","Registro"},
                {"authorisation","Autorização"},
                {"sign_in","Entrar"},
                {"registration_failed", "Registração falhou. Tente outros dados."},
                {"authorisation_failed", "Falha na autorização. Tente outros dados."},

                {"city.input_city", "Entrada da cidade"},
                {"city.city_name", "Nome"},
                {"city.coord_x", "Coordenada X"},
                {"city.coord_y", "Coordenada Y"},
                {"city.area", "Quadrado"},
                {"city.population", "População"},
                {"city.meters_above", "Altura acima do nível do mar"},
                {"city.timezone", "Fuso horário"},
                {"city.agglomeration", "Aglomeração"},
                {"city.climate", "Clima"},
                {"city.governor", "prefeito"},
                {"city.governor_name", "Nome"},
                {"city.governor_age", "Era"},
                {"city.governor_birthday", "Data de nascimento"},
                {"city.ready", "Preparar"},
                {"city.cancel", "Cancelar"},
                {"city.error_in_field", "Erro de campo"},

                {"main.table", "Mesa"},
                {"main.creation_time", "Tempo de criação"},
                {"main.author", "Autor"},
                {"main.governor_name", "nome do prefeito"},
                {"main.governor_age", "idade do prefeito"},
                {"main.governor_birthday", "Data de nascimento do prefeito"},
                {"main.graphics", "Exibição gráfica"},
                {"main.management", "Ao controle"},
                {"main.current_user", "Usuário atual: "},
                {"main.need_enter", "Você deve entrar"},
                {"main.script_ready", "Script concluído"},

        };
    }
}
