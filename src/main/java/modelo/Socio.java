package modelo;

import interfaces.Habilitable;

import java.util.ArrayList;
import java.util.List;

public class Socio extends Persona implements Habilitable {
    private static int contadorSocio = 1;
    private int numeroSocio;
    private Membresia paseSala;
    private List<SesionGuiada> sesionesInscriptas;
    private Rutina rutinaPersonal;


    public Socio(String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono);
        this.numeroSocio = contadorSocio++;
        this.sesionesInscriptas = new ArrayList<>();
    }

    public boolean tienePaseSalaActivo(){
        return this.paseSala != null && this.paseSala.estaActivo();
    }

    @Override
    public boolean estaActivo() {
        if (tienePaseSalaActivo()) {
            return true;
        }
        for (SesionGuiada s : this.sesionesInscriptas) {
            if (s.estaActivo()) {
                return true;
            }
        }
        return false;
    }

    public void agregarSesion(SesionGuiada sesion) {
        if (!this.sesionesInscriptas.contains(sesion)) {
            this.sesionesInscriptas.add(sesion);
        }
    }

    public void removerSesion(SesionGuiada sesion) {
        this.sesionesInscriptas.remove(sesion);
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public Membresia getPaseSala() {
        return paseSala;
    }

    public void setPaseSala(Membresia paseSala) {
        this.paseSala = paseSala;
        if (paseSala == null) {
            this.rutinaPersonal = null;
        }
    }

    public List<SesionGuiada> getSesionesInscriptas() {
        return sesionesInscriptas;
    }

    public Rutina getRutinaPersonal() {
        return rutinaPersonal;
    }

    public void setRutinaPersonal(Rutina rutinaPersonal) {
        this.rutinaPersonal = rutinaPersonal;
    }
}
