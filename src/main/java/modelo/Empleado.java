package modelo;

import enums.Rol;

public abstract class Empleado extends Persona{
    private String legajo;
    private Rol rolEmpleado;

    public Empleado(int id, String nombre, String dni, String email, String telefono, String usuario, String contrasenia, String legajo, Rol rolEmpleado) {
        super(id, nombre, dni, email, telefono, usuario, contrasenia);
        this.legajo = legajo;
        this.rolEmpleado = rolEmpleado;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public Rol getRolEmpleado() {
        return rolEmpleado;
    }

    public void setRolEmpleado(Rol rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
    }
}