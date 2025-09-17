import  java.util.Scanner;

    public class Factorial {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese un numero");
            int num1 = scanner.nextInt();
            long resultado = 1;
            for (int i = 1; i <= num1; i++){
                resultado *= i;
            }
            System.out.println("El factorial de: " + num1 + " Es :" + resultado );
        }
    }
