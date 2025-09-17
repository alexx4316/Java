import java.util.Scanner;
public class diasSemana {
    public static void main(String[] args){
      Scanner scanner = new Scanner(System.in);
      int dia;
        do {
            System.out.println("Ingrese un numero de los dias de la semana (1 - 7): ");
            dia = scanner.nextInt();
            switch (dia){
                case 1:
                    System.out.println("Lunes");
                    break;
                case 2:
                    System.out.println("Martes");
                    break;
                case 3:
                    System.out.println("Miercoles");
                    break;
                case 4:
                    System.out.println("Jueves");
                    break;
                case 5:
                    System.out.println("Viernes");
                    break;
                case 6:
                    System.out.println("Sabado");
                    break;
                case 7:
                    System.out.println("Domingo");
                    break;
                case 8:
                    System.out.println("Salir");
                    break;
                default:
                    System.out.println("El numero ingresado no corresponde a un dia de la semana");

            };
        } while (dia != 8);
    };
}
