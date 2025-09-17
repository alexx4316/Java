package model;

import javax.swing.*;

public class Asignatura {
    private String nombreAsignatura;
    private double nota;

    public Asignatura(String nombre, double nota) {
        this.nombreAsignatura = nombre;
        this.nota = nota;
    }

    public String getNombre() {
        return nombreAsignatura;
    }

    public void setNombre(String nombre) {
        this.nombreAsignatura = nombre;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String estado() {
        return nota >= 3.5 ? "Aprobado" : "Desaprobado";
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "nombreAsignatura='" + nombreAsignatura + '\'' +
                ", nota=" + nota +
                ", estado" + estado() +
                '}';
    }
}
