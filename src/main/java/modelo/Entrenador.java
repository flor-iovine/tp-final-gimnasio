/**
 * Clase Entrenador que representa a un empleado especializado en el dictado de entrenamientos o clases, extendiendo de Empleado.
 * Gestiona la especialidad correspondiente del entrenador:
 * - Constructor: Inicializa los datos heredados de empleado (nombre, DNI, email, teléfono, usuario y contraseña) y asigna su especialidad.
 * - Métodos getter y setter: Permiten consultar y modificar la especialidad del entrenador.
 */
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
