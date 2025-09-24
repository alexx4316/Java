package model;

import Interfaces.AlumnoInterface;

public abstract class Persona implements AlumnoInterface{
    private String nombre;
    private int edad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    @Override
    public void mostrarInfo(Alumno alumno) {

    }

    @Override
    public void agregarAsignatura(Asignatura nueva, Alumno alumno) {

    }

    @Override
    public void eliminarAsignatura() {

    }

    @Override
    public void editarAsignatura() {

    }

    abstract void agregarAsignatura();
}
