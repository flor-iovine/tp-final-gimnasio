package modelo;

import enums.Rol;

public class Administrador extends Empleado {

    public Administrador(int id, String nombre, String dni, String email, String telefono, String usuario, String contrasenia, String legajo) {
        super(id, nombre, dni, email, telefono, usuario, contrasenia, legajo, Rol.ADMINISTRADOR);
    }
}
