import java.util.Scanner;
public class Calculadora {
    public int sumar(int a, int b){
        return a + b;
    }

    public int restar(int a, int b){
        return a - b;
    };

    public int dividir(int a, int b){
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero");
        };
        return a / b;
    };

    public int multiplicar(int a, int b){
        return a * b;
    };

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Calculadora calc = new Calculadora();

        int opt = 0;
        do {
            System.out.println("Bienvenido a la Calculadora super calculona");
            System.out.println("Ingrese el primer numero: ");
            int num1 = scanner.nextInt();
            System.out.println("Ingrese el segundo numero: ");
            int num2 = scanner.nextInt();

            System.out.println("Seleccione la operacion a realizar: ");
            System.out.println("1. Sumar");
            System.out.println("2. Restar");
            System.out.println("3. Multiplicar");
            System.out.println("4. Dividir");
            System.out.println("5. Salir");
             opt = scanner.nextInt();

            switch (opt){
                case 1:
                    System.out.println("Resultado: " + calc.sumar(num1, num2));
                case 2:
                    System.out.println("Resultado: " + calc.restar(num1, num2));
                case 3:
                    System.out.println("Resultado: " + calc.multiplicar(num1, num2));
                case 4:
                    System.out.println("Resultado: " + calc.dividir(num1, num2));
                case 5:
                    System.out.println("5. Salir");
            };
        } while (opt != 5);
            System.out.println("Gracias por usar la calculadora super calculona");
    };
}


