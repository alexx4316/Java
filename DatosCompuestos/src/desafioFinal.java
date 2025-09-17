import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class desafioFinal {
    public static void main(String[] args) {
        int[] codigos = {123, 456, 789};
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Panchita");
        nombres.add("Otulio");
        nombres.add("Fermina");
        HashMap<String, Integer> cantidad = new HashMap<>();
        cantidad.put("Panchita", 300);
        cantidad.put("Otulio", 500);
        cantidad.put("Fermina", 1000);

        String claveMax = null;
        int maxCantidad = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : cantidad.entrySet()){
            if (entry.getValue() > maxCantidad){
                maxCantidad = entry.getValue();
                claveMax = entry.getKey();
                System.out.println("Clave con mayor cantidad:" + claveMax);
                System.out.println("Cantidad:" + maxCantidad);
            }
        };
    }
}
