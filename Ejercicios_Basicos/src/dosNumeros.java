import java.util.Scanner;
public class dosNumeros {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese dos numeros Enteros");
        System.out.println("Numero 1: ");
        int num1 = scanner.nextInt();
        System.out.println("Numero 2: ");
        int num2 = scanner.nextInt();

        if(num1 > 0 && num2 > 0){
            System.out.println("Ambos numeros son positivos");
        }
        if (num1 > 100 || num2 > 100) {
            System.out.println("Un numero es mayor a 100");
        }
        if (num1 != num2) {
            System.out.println("El primer numero es diferente al segundo");
        }
    };
}
