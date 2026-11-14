package modelo;

import enums.EspecialidadEntrenador;

public class Entrenador extends Empleado {
    private EspecialidadEntrenador especialidad;

    public Entrenador(int id, String nombre, String dni, String email, String telefono, String usuario, String contrasenia, String legajo, EspecialidadEntrenador especialidad) {
        super(id, nombre, dni, email, telefono, usuario, contrasenia, legajo);
        this.especialidad = especialidad;
    }

    public EspecialidadEntrenador getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadEntrenador especialidad) {
        this.especialidad = especialidad;
    }
}
