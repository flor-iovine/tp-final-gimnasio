package modelo;

public abstract class Empleado extends Persona {
    private String legajo;

    public Empleado(int id, String nombre, String dni, String email, String telefono, String usuario, String contrasenia, String legajo) {
        super(id, nombre, dni, email, telefono, usuario, contrasenia);
        this.legajo = legajo;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }
}