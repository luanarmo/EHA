import java.util.*;

public class EHA {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> bc = new ArrayList<>();
        List<Regla> reglas = new ArrayList<>();
        bc.add("caballo-x&padre-x,y&rapido-y>rapido-x");
        bc.add("caballo-x&rapido-x>candidatocompeticion-x");
        for (Regla r : convertir(bc))
            reglas.add(r);

        BaseHechos bh = new BaseHechos();
        List<String> dominio1 = new ArrayList<>();
        dominio1.add("cometa");
        dominio1.add("veloz");
        dominio1.add("bronco");
        dominio1.add("rayo");
        bh.agregarDominios("x", dominio1);
        bh.agregarDominios("y", dominio1);
        bh.agregarHechos("caballo-cometa");
        bh.agregarHechos("caballo-bronco");
        bh.agregarHechos("caballo-veloz");
        bh.agregarHechos("caballo-rayo");
        bh.agregarHechos("padre-cometa,veloz");
        bh.agregarHechos("padre-cometa,bronco");
        bh.agregarHechos("padre-veloz,rayo");
        bh.agregarHechos("rapido-bronco");
        bh.agregarHechos("rapido-rayo");


//        List<String> preguntas = preguntar(bc);
        int R;
        List<Predicado> nuevosHechos;
        List<Integer> conjuntoConflicto = new ArrayList<>();
//        conjuntoConflicto.add(extraeRegla(bc));

//        while (!noVacio(bc) && !noVacio(conjuntoConflicto)) {
        conjuntoConflicto = equiparar(antecedente(reglas), bh);
        //System.out.println(conjuntoConflicto.toString());

//            if (!noVacio(conjuntoConflicto)) {
        R = resolver(conjuntoConflicto);
        System.out.println(R);
        nuevosHechos = aplicar(R, reglas, bh, new ArrayList<>());
//                System.out.println(nuevosHechos.toString());
//                if (!nuevosHechos.isEmpty()) {
//                    System.out.printf("Submeta: '%s'\n", nuevosHechos);
//                    actualizar(bh.getHechos(), nuevosHechos);
//                    System.out.println(bh.toString());
//                    conjuntoConflicto = equiparar(antecedente(bc), bh.getHechos());
//                }
//            }
//        }
        /*int max = 0;
        for (String string : bh.getHechos()) {
            if (valores.get(string) > max) {
                max = valores.get(string);
            }
        }
        System.out.printf("Resultado: ");
        for (String str : bh.getHechos()) {
            if (valores.get(str) == max) {
                System.out.println(str);
            }
        }*/
    }

    static List<Regla> convertir(List<String> bc) {
        List<Regla> reglas = new ArrayList<>();
        for (String s : bc) {
            String[] revision = s.split(">");
            String[] antecedentes = revision[0].split("&");
            List<Predicado> p = new ArrayList<>();
            for (String a : antecedentes) {
                String[] predicados = a.split("-");
                String[] variables = predicados[1].split(",");
                p.add(new Predicado(predicados[0], Arrays.asList(variables)));
            }
            String[] aux = revision[1].split("-");
            String[] aux1 = aux[1].split(",");
            reglas.add(new Regla(p, new Predicado(aux[0], Arrays.asList(aux1))));
        }

        return reglas;
    }

    static List<String> preguntar(List<String> bc) {
        Set<String> antecedentes = new HashSet<>();
        Set<String> consecuentes = new HashSet<>();

        for (String regla : bc) {
            String[] aux = regla.split(">");
            consecuentes.add(aux[1]);
            for (String s : aux[0].split("&"))
                antecedentes.add(s);
        }

        antecedentes.removeAll(consecuentes);
        return new ArrayList<>(antecedentes);
    }

    boolean revisa(List<String> antecedente, List<String> bh) {
        for (String l : antecedente) {
            if (!antecedente.contains(l)) {
                return false;
            }
        }
        return true;
    }

    static List<Integer> equiparar(List<List<Predicado>> antecedente, BaseHechos bh) {
        boolean add;
        int cont = -1;
        List<Integer> list = new ArrayList<>();
        for (List<Predicado> predicados : antecedente) {
            add = false;
            for (int i = 0; i < predicados.size(); i++)
                for (int j = 0; j < bh.getHechos().size(); j++) {
                    List<Boolean> auxilio = new ArrayList<>();
                    if (bh.getHechos().get(j).getNombre().equals(predicados.get(i).getNombre()) &&
                            bh.getHechos().get(j).getVariables().size() == predicados.get(i).getVariables().size()) {
                        for (int k = 0; k < predicados.get(i).getVariables().size(); k++) {
                            List<String> dominio = bh.getDominios().get(predicados.get(i).getVariables().get(k));
                            if (dominio.contains(bh.getHechos().get(j).getVariables().get(k))) {
                                auxilio.add(true);
                            } else {
                                auxilio.add(false);
                            }
                        }
                    }
                    if (!auxilio.contains(false)) {
                        add = true;
                        break;
                    }
                }
            cont++;
            if (add)
                list.add(cont);
        }
        return list;
    }

    static List<List<Predicado>> antecedente(List<Regla> bc) {
        List<List<Predicado>> antecedentes = new ArrayList<>();
        bc.forEach((b) -> {

            antecedentes.add(b.getAntecedentes());
        });
        return antecedentes;
    }

    static String extraeRegla(List<String> bc) {
        return bc.get(0);
    }

    static boolean noContenida(String meta, List<String> bh) {
        return !bh.contains(meta);
    }

    static boolean noVacio(List<String> ConjuntoConflicto) {
        return ConjuntoConflicto.size() == 0;
    }

    static int resolver(List<Integer> ConjuntoConflicto) {

        int conjunto = ConjuntoConflicto.get(0);
        //ConjuntoConflicto.remove(0);
        //String[] aux2 = conjunto.split("-");
        return conjunto;
    }

    static List<Predicado> aplicar(int R, List<Regla> bc, BaseHechos bh, List<String> preguntas) {
        Map<String, List<String>> variables = new HashMap<>();
        Regla regla = bc.get(R);
        regla.aumentarContador();
        System.out.println(regla.toString());
        List<String> valores = null;
        List<Predicado> predicados = new ArrayList<>();
        for (Predicado predicado : regla.getAntecedentes()) {
            List<Predicado> coincidencias = igualar(predicado, bh);
            System.out.println(coincidencias.toString());
            if (coincidencias.size() > 0) {
                for (int i = 0; i < predicado.getVariables().size(); i++) {
                    if (variables.get(predicado.getVariables().get(i)) == null) {
                        Set<String> setCoincidencias = new HashSet<>();
                        for (Predicado coin : coincidencias) {
                            setCoincidencias.add(coin.getVariables().get(i));
                        }
                        variables.put(predicado.getVariables().get(i), new ArrayList<>(setCoincidencias));
                    } else {
                        Set<String> setValores = new HashSet<>(variables.get(predicado.getVariables().get(i)));
                        Set<String> setCoincidencias = new HashSet<>();
                        for (Predicado coin : coincidencias) {
                            setCoincidencias.add(coin.getVariables().get(i));
                        }
                        setValores.retainAll(setCoincidencias);
                        variables.put(predicado.getVariables().get(i), new ArrayList<>(setValores));
                    }
                }
            }
        }
        System.out.println(variables);
        for(String variable:regla.getConsecuente().getVariables()) {
            predicados.add(new Predicado());
        }
        return new ArrayList<>();
    }

    static List<Predicado> igualar(Predicado predicado, BaseHechos bh) {
        List<Predicado> foo = new ArrayList<>();
        for (int j = 0; j < bh.getHechos().size(); j++) {
            List<Boolean> auxilio = new ArrayList<>();
            if (bh.getHechos().get(j).getNombre().equals(predicado.getNombre()) &&
                    bh.getHechos().get(j).getVariables().size() == predicado.getVariables().size()) {
                for (int k = 0; k < predicado.getVariables().size(); k++) {
                    List<String> dominio = bh.getDominios().get(predicado.getVariables().get(k));
                    if (dominio.contains(bh.getHechos().get(j).getVariables().get(k))) {
                        auxilio.add(true);
                    } else {
                        auxilio.add(false);
                    }
                }
            }
            if (!auxilio.contains(false) && !auxilio.isEmpty()) {
                foo.add(bh.getHechos().get(j));
            }
        }
        return foo;
    }

    static void actualizar(List<String> bh, String nuevosHechos) {
        bh.add(nuevosHechos);
    }
}