package model;

public class Alumno extends Persona {
    private Asignatura[] asignaturas;
    private int cantidadAsignaturas;

    public Alumno(String nombre, int edad) {
        super(nombre,edad);
        this.asignaturas = new Asignatura[4];
        this.cantidadAsignaturas = 0;
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

    public String agregarAsignatura(Asignatura nueva){
        if (cantidadAsignaturas == 4){
            return "No puede inscribir mas materias";
        } else {
            for (int i = 0; i < cantidadAsignaturas ; i++) {
                if (asignaturas[i].getNombre().equalsIgnoreCase(nueva.getNombre())){
                    return "La asignatura ya existe";
                }
            }
        }
        asignaturas[cantidadAsignaturas] = nueva;
        cantidadAsignaturas++;

        return "Asignatura agregada correctamente";
    }

    public void mostrarInfo(){
        System.out.println("Alumno: " + getNombre() + " | Edad: " + getEdad());
        for (int i = 0; i < cantidadAsignaturas; i++) {
            Asignatura a = asignaturas[i];
            System.out.println(" - " + a.getNombre() + " | Nota: " + a.getNota() + " | " + a.estado());
        }
        System.out.println("Promedio" + promedio());
    }
}
