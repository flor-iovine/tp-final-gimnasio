package modelo;

public class Socio extends Persona{
    private int numeroSocio;
    private boolean estadoCuota;
    private Membresia membresiaActiva;

    public Socio(int id, String nombre, String dni, String email, String telefono, String usuario, String contrasenia, int numeroSocio) {
        super(id, nombre, dni, email, telefono, usuario, contrasenia);
        this.numeroSocio = numeroSocio;
        this.estadoCuota = false;
    }

    public boolean isEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(boolean estadoCuota) {
        this.estadoCuota = estadoCuota;
    }

    public Membresia getMembresiaActiva() {
        return membresiaActiva;
    }

    public void setMembresiaActiva(Membresia membresiaActiva) {
        this.membresiaActiva = membresiaActiva;
    }
}
