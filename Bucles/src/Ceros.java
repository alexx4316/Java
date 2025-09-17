import java.util.Scanner;
public class Ceros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num;
        int mayoresCero = 0;
        int menoresCero = 0;
        int ceros = 0;
        int cantNum;

        System.out.println("Ingrese la cantidad de numeros que quiere ingresar: ");
        cantNum = scanner.nextInt();

        for (int i = 0; i < cantNum; i++) {
            System.out.println("Ingrese los numeros: ");
            num = scanner.nextInt();

            if (num < 0){
                menoresCero++;
            } else if (num > 0) {
                mayoresCero++;
            } else {
                ceros++;
            }
        }
        System.out.println("Los numeros mayores a 0 son: " + mayoresCero + " Los numeros menores a 0 son: " + menoresCero + " La cantidad de numeros iguales a 0 son: " + ceros);

    }
}
