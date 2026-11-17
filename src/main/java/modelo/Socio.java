/**
 * Clase Socio que representa a un miembro del gimnasio, extendiendo de Persona e implementando la interfaz Habilitable.
 * Gestiona su membresía (pase de sala), sus sesiones guiadas inscriptas y su rutina personal:
 * - Constructor: Inicializa los datos heredados y crea una lista vacía de sesiones inscriptas.
 * - tienePaseSalaActivo: Valida si el socio posee un pase de sala asignado y si este se encuentra activo.
 * - estaActivo: Evalúa si el socio está activo, retornando true si tiene un pase de sala activo o si alguna de sus sesiones inscriptas está activa.
 * - agregarSesion / removerSesion: Añaden o eliminan sesiones guiadas de la lista de inscripciones del socio.
 * - Métodos getter y setter: Permiten consultar y modificar el pase de sala, las sesiones inscriptas y la rutina personal.
 */

package modelo;

import interfaces.Habilitable;

import java.util.ArrayList;
import java.util.List;

public class Socio extends Persona implements Habilitable {
    private Membresia paseSala;
    private List<SesionGuiada> sesionesInscriptas;
    private Rutina rutinaPersonal;


    public Socio(String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono);
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
