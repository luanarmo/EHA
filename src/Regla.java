import java.util.List;

public class Regla {
    List<Predicado> antecedentes;
    Predicado consecuente;

    public Regla(List<Predicado> antecedentes, Predicado consecuente) {
        this.antecedentes = antecedentes;
        this.consecuente = consecuente;
    }

    public List<Predicado> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(List<Predicado> antecedentes) {
        this.antecedentes = antecedentes;
    }

    public Predicado getConsecuente() {
        return consecuente;
    }

    public void setConsecuente(Predicado consecuente) {
        this.consecuente = consecuente;
    }

    @Override
    public String toString() {
        return "Regla{" +
                "antecedentes=" + antecedentes +
                ", consecuente=" + consecuente.toString() +
                '}';
    }
}
