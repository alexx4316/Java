import java.util.ArrayList;
public class mochilaMagica {
    public static void main(String[] args) {
        ArrayList<String> mochila = new ArrayList<String>();
        mochila.add("Espada");
        mochila.add("Mapa");
        mochila.add("Pocion");
        System.out.println(mochila.get(1));
        mochila.remove(1);
        System.out.println(mochila);
    }
}
