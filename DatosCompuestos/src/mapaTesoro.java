import java.util.HashMap;
public class mapaTesoro {
    public static void main(String[] args) {
        HashMap<String, Integer> tesoros = new HashMap<>();
        tesoros.put("Oro", 100);
        tesoros.put("Plata", 50);
        tesoros.put("Diamante", 500);
        System.out.println(tesoros);
        System.out.println(tesoros.get("Diamante"));
        tesoros.put("Oro", 200);
        System.out.println(tesoros);
    }
}
