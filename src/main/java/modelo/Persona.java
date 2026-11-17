/**
 * Clase abstracta Persona que representa los datos y atributos comunes de cualquier persona dentro del sistema.
 * Utiliza un contador estático para autoincrementar un identificador único (id) por cada instancia creada:
 * - Constructor: Asigna un ID único y configura los atributos básicos (nombre, dni, email y teléfono).
 * - Métodos getter y setter: Permiten consultar y modificar el identificador, nombre, DNI, correo electrónico y teléfono.
 */

package modelo;

public abstract class Persona {
    private static int contadorId = 1;
    private int id;
    private String nombre;
    private String dni;
    private String email;
    private String telefono;

    public Persona(String nombre, String dni, String email, String telefono) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
