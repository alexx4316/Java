import java.util.Scanner;
public class Vocales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char letraChar;
        do {
            System.out.println("Ingrese un caracter: ");
            String letra = scanner.nextLine();
            letraChar = letra.charAt(0);
            if (letraChar == ' '){
                System.out.println("Programa finalizado");
                break;
            }
            if ("aeiouAEIOU".indexOf(letraChar) != -1){
                System.out.println("Vocal");
            } else {
                System.out.println("No vocal");
            }
        } while (letraChar != ' ');
        scanner.close();
    }
}
