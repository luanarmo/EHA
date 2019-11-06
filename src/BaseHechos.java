import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseHechos {
    Map<String,List<String>> dominios;
    List<List<String>> actuales;
    List<String> hechos;

    public BaseHechos(Map<String,List<String>> dominios, List<List<String>> actuales, List<String> hechos) {
        this.dominios = dominios;
        this.actuales = actuales;
        this.hechos = hechos;
    }

    public BaseHechos() {
        this.dominios = new HashMap<>();
        this.actuales = new ArrayList<>();
        this.hechos = new ArrayList<>();
    }

    public Map<String,List<String>> getDominios() {
        return dominios;
    }

    public void agregarDominios(String variable,List<String> dominios) {
        this.dominios.put(variable,dominios);
    }

    public List<List<String>> getActuales() {
        return actuales;
    }

    public void agregarActuales(List<String> actuales) {
        this.actuales.add(actuales);
    }

    public List<String> getHechos() {
        return hechos;
    }

    public void agregarHechos(String hechos) {
        this.hechos.add(hechos);
    }
}
