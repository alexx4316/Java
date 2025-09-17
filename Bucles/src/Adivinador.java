import java.util.Scanner;
import java.util.Random;
public class Adivinador {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int num;
        int numSecret = random.nextInt(100) + 1;
        boolean adivino = false;
        System.out.println("Bienvenido a la adivinadora");
        System.out.println("He generado un numero aleatorio del 1 al 100");
        do {
            System.out.println("Ingrese un numero (Precione 0 para salir): ");
            num = scanner.nextInt();

            if (num == numSecret){
                System.out.println("Gano mi papacho");
            } else if (Math.abs(num - numSecret) <= 5) {
                System.out.println("Muy cerca");
            } else if (num < numSecret) {
                System.out.println("Mas alto");
            } else {
                System.out.println("Mas bajo");
            }

        } while (num != 0);
    }
}
