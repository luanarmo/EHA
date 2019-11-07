import java.util.*;

public class BaseHechos {
    Map<String,List<String>> dominios;
    List<List<String>> actuales;
    List<Predicado> hechos;

    public BaseHechos(Map<String,List<String>> dominios, List<List<String>> actuales, List<Predicado> hechos) {
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

    public List<Predicado> getHechos() {
        return hechos;
    }

    public void agregarHechos(String hechos) {
        String[] aux = hechos.split("-");
        String[] aux1 = aux[1].split(",");
        this.hechos.add(new Predicado(aux[0], Arrays.asList(aux1)));
    }
}
