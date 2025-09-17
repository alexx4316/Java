import java.util.Scanner;
public class PalabraRepetida {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String [] palabras = {"hola", "mundo", "hola"};
        System.out.println("Ingrese la palabra que desea buscar: ");
        String palabraBuscada = scanner.nextLine();
        int contadorP = 0;
        for (String palabra : palabras){
            if (palabra.equalsIgnoreCase(palabraBuscada)){
                contadorP++;
            }
        };
        if (contadorP > 0){
            System.out.println("La palabra " + palabraBuscada + " Aparece: " + contadorP + " veces");
        } else {
            System.out.println("La palabra" + palabraBuscada + " no se encuentra en el arreglo");
        }
    }
}
