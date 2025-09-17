import java.util.Scanner;
public class SumaTotal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num;
        int suma = 0;
        do {
            System.out.println("Intoduce numeros para sumar (0 para salir): ");
            num = scanner.nextInt();
            suma += num;
        } while (num != 0);
        System.out.println("La sumatoria total es: " + suma );
    }
}
