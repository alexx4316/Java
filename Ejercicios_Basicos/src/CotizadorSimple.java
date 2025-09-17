import java.util.Scanner;
public class CotizadorSimple {

    enum plan {
        Basico (80.0),
        Premium (120.0),
        Plus(180.0);

        private final double precio;

        plan(double precio) {
            this.precio = precio;
        }

        public double getPrecio() {
            return precio;
        }
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nombre;
        int edad;
        double altura;
        double peso;
        double descuento = 0.0;
        boolean prmVez;

        // Solicitar datos
        System.out.println("Ingrese su nombre: ");
        nombre = scanner.nextLine();

        System.out.println("Ingrese su edad: ");
        edad = scanner.nextInt();

            if (edad < 14){
                System.out.println("No elegible");
                return;
            } else if (edad < 18 ){
                System.out.println("Requiere autorizacion de un adulto");
            };

        System.out.println("Ingrese su altura en metros (Ejemplo 1.75): ");
        altura = scanner.nextDouble();

        System.out.println("Ingrese su peso en kg (Ejemplo 70.5): ");
        peso = scanner.nextDouble();

        System.out.println("Es su primer vez? (true/false): ");
        prmVez = scanner.nextBoolean();

        System.out.println("Seleccione un plan: ");
        for (plan p : plan.values()) {
            System.out.println(p.ordinal() + 1 + ". " + p.name() + " ($" + p.getPrecio() + ")");
        }
        int opcion = scanner.nextInt();
        plan planselecionado = plan.values()[opcion - 1];

        double bmi = peso / (altura * altura);
        String categoriaBmi;
        if (bmi < 18.5){
            categoriaBmi = "Bajo peso";
        } else if (bmi < 24.9){
            categoriaBmi = "Normal";;
        } else if (bmi < 29.9){
            categoriaBmi = "Sobrepeso";;
        } else {
            categoriaBmi = "Obesidad";;
        };

        // Descuentos
        if (prmVez){
            descuento += 0.10;
            System.out.println("Descuento del 10% por ser primer vez");
        }

        if (edad >= 16 && edad <= 25){
            descuento += 0.10;
            System.out.println("Descuento del 10% por tener entre 16 y 25 años");
        }

        if (descuento >= 0.20){
            descuento = 0.20;
            System.out.println("El descuento no puede ser superiror a 20%");
        };

        // Precio final
        double precioBase = planselecionado.getPrecio();
        double precioFinal = precioBase * (1 - descuento);

        // Resultado
        System.out.println("Cotizacion final para " + nombre + ":");
        System.out.println("Plan seleccionado: " + planselecionado.name() + " ($" + precioBase + ")");
        System.out.println("Descuento aplicado: " + (descuento * 100) + "%");
        System.out.println("Precio final: $" + precioFinal);
        System.out.println("Categoria BMI: " + categoriaBmi);
    };
};
