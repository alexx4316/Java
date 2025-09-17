import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numSecret = random.nextInt(100) + 1;
        int intentos = 10;
        int intentosConsumidos = 0;
        int numUsuario;
        boolean adivino = false;

        System.out.println("Bienvenido al adivinador maximo");
        System.out.println("Se ha generado un numero aleatoria del 1 al 100");

        while (intentosConsumidos < intentos && !adivino){
            System.out.println("intento # : " + ( intentosConsumidos + 1) + " te quedan : " + ( intentos - intentosConsumidos) + " intentos");
            System.out.println("Introduce el numero: ");
            numUsuario = scanner.nextInt();
            intentosConsumidos++;
            if (numUsuario == numSecret){
                adivino = true;
                System.out.println("Lo gano de chimbaso");
            } else if (numUsuario < numSecret) {
                System.out.println("El Numero secreto es MAYOR");
            } else {
                System.out.println("El numero secreto es MENOR");
            };
        };
        if (!adivino) {
            System.out.println("Has gastado todos tus intentos: " + intentosConsumidos);
            System.out.println("El numero secreto es: " + numSecret);
        };
        scanner.close();
    }
}