import java.util.Scanner;
public class edades {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su edad:");
        int num1 = scanner.nextInt();

        if(num1 < 12){
            System.out.println("Niño");
        } else if (num1 >= 12 && num1 <= 17) {
            System.out.println("Adolecente");
        } else if (num1 >= 18) {
            System.out.println("Adulto");
        }
    };
}
