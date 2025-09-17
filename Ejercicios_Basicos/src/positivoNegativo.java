import java.util.Scanner;
public class positivoNegativo {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un numero: ");
        int num1 = scanner.nextInt();

        if (num1 > 0){
            System.out.println("El numero es positivo");
        } else if (num1 < 0) {
            System.out.println("El numero es negativo");
        } else if (num1 == 0) {
            System.out.println("El numero es exactamente 0");
        }
    };
}
