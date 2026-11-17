package modelo;

import enums.EspecialidadEntrenador;

public class Entrenador extends Empleado {
    private EspecialidadEntrenador especialidad;

    public Entrenador(String nombre, String dni, String email, String telefono, String usuario, String contrasenia, EspecialidadEntrenador especialidad) {
        super(nombre, dni, email, telefono, usuario, contrasenia);
        this.especialidad = especialidad;
    }

    public EspecialidadEntrenador getEspecialidad() { return especialidad; }
    public void setEspecialidad(EspecialidadEntrenador especialidad) { this.especialidad = especialidad; }
}
