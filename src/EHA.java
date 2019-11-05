import java.util.*;

public class EHA {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> bc = new ArrayList<>();
        bc.add("b&d&e>f");
        bc.add("d&g>a");
        bc.add("c&f>a");
        bc.add("b>x");
        bc.add("d>e");
        bc.add("x&a>h");
        bc.add("c>d");
        bc.add("x&c>a");
        bc.add("x&b>d");

        List<String> bh = new ArrayList<>();
        Map<String, Integer> valores = new HashMap<>();
        valores.put("h", 100);
        valores.put("a", 90);
        valores.put("x", 90);
        valores.put("f", 80);
        valores.put("d", 70);
        valores.put("e", 70);
        valores.put("g", 60);
        valores.put("c", 50);
        valores.put("b", 50);

//        bh.add("b");
        bh.add("c");
//        bh.add("g");
        //String meta = "h";
        List<String> preguntas = preguntar(bc);
        int R;
        String nuevosHechos;
        List<String> conjuntoConflicto = new ArrayList<>();
        conjuntoConflicto.add(extraeRegla(bc));

        while (!noVacio(bc) && !noVacio(conjuntoConflicto)) {
            conjuntoConflicto = equiparar(antecedente(bc), bh);
            if (!noVacio(conjuntoConflicto)) {
                R = resolver(conjuntoConflicto);
                nuevosHechos = aplicar(R, bc, bh, preguntas);
                if (!nuevosHechos.isEmpty()) {
                    System.out.printf("Submeta: '%s'\n", nuevosHechos);
                    actualizar(bh, nuevosHechos);
                    System.out.println(bh.toString());
                    conjuntoConflicto = equiparar(antecedente(bc), bh);
                }
            }
        }
        int max = 0;
        for (String string : bh) {
            if (valores.get(string) > max) {
                max = valores.get(string);
            }
        }
        System.out.printf("Resultado: ");
        for (String str : bh) {
            if (valores.get(str) == max) {
                System.out.println(str);
            }
        }
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
    boolean revisa(List<String> antecedente, List<String> bh){
        for (String l :antecedente) {
            if(!antecedente.contains(l))
            {
                return false;
            }
        }
        return true;
    }

    static List equiparar(List<String> antecedente, List<String> bh) {
        boolean add;
        int cont = -1;
        List<String> list = new ArrayList<>();
        for (String string : antecedente) {
            add = false;
            String[] vals = string.split("&");
            for (int i = 0; i < vals.length; i++)
                if (bh.contains(vals[i]))
                    add = true;
                else {
                    add = false;
                    break;
                }
            cont++;
            if (add)
                list.add(string + "-" + cont);
        }
        return list;
    }

    static List<String> antecedente(List<String> bc) {
        List<String> antecedentes = new ArrayList<>();
        bc.forEach((b) -> {
            String[] array = b.split(">");
            antecedentes.add(array[0]);
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

    static int resolver(List<String> ConjuntoConflicto) {
        String conjunto = ConjuntoConflicto.get(0);
        ConjuntoConflicto.remove(0);
        String[] aux2 = conjunto.split("-");
        int indice = Integer.parseInt(aux2[1]);
        return indice;
    }

    static String aplicar(int R, List<String> bc, List<String> bh, List<String> preguntas) {
        String regla = bc.get(R);
        bc.remove(R);
        String[] aux = regla.split(">");
        String[] antecedentes = aux[0].split("&");
        for (String n : antecedentes) {
            if (!bh.contains(n) && preguntas.contains(n)) {
                System.out.println("Tienes " + n);
                //scan.nextLine();
                String r = scan.nextLine();
                System.out.printf("R: '%s'\n", r);
                if (r.equals("s")) {
                    bh.add(n);
                } else {
                    System.out.println("No se puede aplicar la regla " + R);
                    return "";
                }
            }
        }
        return "" + aux[1];
    }

    static void actualizar(List<String> bh, String nuevosHechos) {
        bh.add(nuevosHechos);
    }
}