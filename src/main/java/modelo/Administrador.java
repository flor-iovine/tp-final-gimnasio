/**
 * Clase Administrador que representa a un empleado con rol de administrador dentro del sistema, extendiendo de Empleado.
 * - Constructor: Inicializa los datos heredados de empleado (nombre, DNI, email, teléfono, usuario y contraseña).
 */
package modelo;

public class Administrador extends Empleado {
    public Administrador(String nombre, String dni, String email, String telefono, String usuario, String contrasenia) {
        super(nombre, dni, email, telefono, usuario, contrasenia);
    }
}