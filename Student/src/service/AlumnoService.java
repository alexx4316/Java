package service;

import Interfaces.AlumnoInterface;
import model.Alumno;
import model.Asignatura;

import java.util.Scanner;

public class AlumnoService implements AlumnoInterface{
    static Scanner scanner = new Scanner(System.in);

    public static Alumno crearAlumno(){
        //Pedimos datos del alumno
        System.out.println("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese la edad");
        int edad = scanner.nextInt();
        scanner.nextLine();

        return new Alumno(nombre, edad);
    }

    @Override
    public void mostrarInfo(Alumno alumno) {
                System.out.println("==============================");
        System.out.println(" Alumno " + alumno.getNombre() + " Edad " + alumno.getEdad());
        System.out.println(" Asignaturas: ");

        for (int j = 0; j <alumno.getCantidadAsignaturas() ; j++) {
            Asignatura asig = alumno.getAsignaturas()[j];
            System.out.println(" - " + asig.getNombre() + " | Nota: " + asig.getNota() + " | Estado: " + asig.estado());
        }

        System.out.println(" Promedio: " + alumno.promedio());
        System.out.println("================================");
    }

    @Override
    public void agregarAsignatura(Asignatura nueva, Alumno alumno) {
        //Pedimos datos de la materia
        System.out.println("¿Cuantas materias desea ingresar: (Max 4): ");
        int numAsig = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numAsig; i++) {
            System.out.println("Ingrese el nombre de la asignatura: ");
            String nomAsig = scanner.nextLine();

            System.out.println("Ingrese a nota de la materia: ");
            Double nota = scanner.nextDouble();
            scanner.nextLine();

            Asignatura asignatura = new Asignatura(nomAsig, nota);
            alumno.agregarAsignatura(asignatura);
        }
    }

    @Override
    public void eliminarAsignatura() {

    }

    @Override
    public void editarAsignatura() {

    }
}
