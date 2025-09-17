import java.util.Scanner;
public class Piramide {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tamañao de la piramide (num impar): ");
        int num = scanner.nextInt();

        for (int i = 1; i < num ; i++) {
            for (int j = 1; j < 2 * num - 1 ; j++) {
                if (j >= num - (i - 1) && j <= num + (i - 1)){
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
