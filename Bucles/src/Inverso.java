import java.util.Scanner;
public class Inverso {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el numero que quiere ivertir: ");
        String num = scanner.nextLine();

        String invertido = new StringBuilder(num).reverse().toString();

        System.out.println("El numero invertido es: " + invertido);

        if ( num.equals(invertido)){
            System.out.println("Es capicua");
        }
    }
}
