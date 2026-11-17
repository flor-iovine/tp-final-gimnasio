/**
 * Clase abstracta Empleado que representa a un empleado del sistema, extendiendo de Persona e incorporando credenciales de acceso.
 * Gestiona el usuario y la contraseña para la autenticación:
 * - Constructor: Inicializa los datos heredados de persona y configura el usuario y la contraseña.
 * - login: Valida las credenciales comparando el usuario (ignorando mayúsculas/minúsculas) y la contraseña ingresada.
 * - Métodos getter y setter: Permiten consultar y modificar el usuario y la contraseña.
 */
package modelo;

public abstract class Empleado extends Persona {
    private String usuario;
    private String contrasenia;

    public Empleado(String nombre, String dni, String email, String telefono, String usuario, String contrasenia) {
        super(nombre, dni, email, telefono);
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public boolean login(String user, String contra) {
        return this.usuario.equalsIgnoreCase(user) && this.contrasenia.equals(contra);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}