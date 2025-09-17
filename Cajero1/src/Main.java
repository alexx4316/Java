import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double saldo = 1000000;
        int opt;
        int realPassword = 1234;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Cajero Automático");
        System.out.print("Por favor, ingrese su usuario: ");
        String user = scanner.nextLine();

        System.out.print("Por favor, ingresar su contraseña: ");
        int password = scanner.nextInt();

        if (user.equals("user123") && password == realPassword) {
            do {
                System.out.println("\nBienvenido " + user);
                System.out.println("Su saldo actual es: " + saldo);
                System.out.println("Seleccione una opción:");
                System.out.println("1. Retirar dinero");
                System.out.println("2. Depositar dinero");
                System.out.println("3. Pedir avance");
                System.out.println("4. Salir");
                System.out.print("Opción: ");
                opt = scanner.nextInt();

                switch (opt) {
                    case 1:
                        System.out.print("Ingrese la cantidad que quiere retirar: ");
                        double retiro = scanner.nextDouble();
                        if (retiro <= 0) {
                            System.out.println("Cantidad no válida");
                        } else if (retiro > saldo) {
                            System.out.println("Saldo insuficiente, recuerda pagar tu avance anterior");
                        } else {
                            saldo -= retiro;
                            System.out.println("Retiro exitoso, tu nuevo saldo es: " + saldo);
                        }
                        break;

                    case 2:
                        System.out.print("Ingrese la cantidad que quiere depositar: ");
                        double deposito = scanner.nextDouble();
                        if (deposito <= 0) {
                            System.out.println("Cantidad no válida");
                        } else {
                            saldo += deposito;
                            System.out.println("Depósito exitoso, tu nuevo saldo es: " + saldo);
                            if (saldo < 0) {
                                System.out.println("Saldo negativo, recuerda pagar tu avance anterior");
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Ingrese la cantidad que quiere de avance: ");
                        double avance = scanner.nextDouble();
                        if (avance <= 0) {
                            System.out.println("Cantidad no válida");
                        } else {
                            saldo += avance;
                            System.out.println("Avance exitoso, tu nuevo saldo es: " + saldo);
                        }
                        break;

                    case 4:
                        System.out.println("Gracias por usar el cajero automático");
                        break;

                    default:
                        System.out.println("Opción no válida");
                        break;
                }

            } while (opt != 4);
        } else {
            System.out.println("Usuario o contraseña incorrectos");
        }

        scanner.close();
    }
}
