import java.util.Scanner;

public class Edad {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su edad: ");
        int edad = scanner.nextInt();
        System.out.println(edad >= 18);
    };
}
