package app;

import model.Alumno;
import service.AlumnoService;

public class Main {
    public static void main(String[] args) {
        Alumno alumno = AlumnoService.crearAlumno();
        AlumnoService.agregarAsignatura(alumno);
        AlumnoService.mostrarInfo(alumno);
    }
}