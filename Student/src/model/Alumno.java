package model;

public abstract class Alumno extends Persona{
    private Asignatura[] asignaturas = new Asignatura[4];
    private int cantidadAsignaturas = 0;

    public Alumno(String nombre, int edad) {
        super(nombre, edad);
    }


    public double promedio(){
        if (cantidadAsignaturas == 0) return 0;
        double suma = 0;
        for (int i = 0; i < cantidadAsignaturas ; i++) {
            suma += asignaturas[i].getNota();
        }
        double prom = (suma / cantidadAsignaturas);
        return prom;
    }

    public int getCantidadAsignaturas() {
        return cantidadAsignaturas;
    }

    public Asignatura[] getAsignaturas() {
        return asignaturas;
    }


    @Override
    public void eliminarAsignatura() {

    }

    @Override
    public void agregarAsignatura(Asignatura nueva, Alumno alumno) {

    }
}
