package Interfaces;

import model.Alumno;
import model.Asignatura;

public interface AlumnoInterface {
    void mostrarInfo(Alumno alumno);

    void agregarAsignatura(Asignatura nueva, Alumno alumno);

    void eliminarAsignatura();

    void editarAsignatura();

}

