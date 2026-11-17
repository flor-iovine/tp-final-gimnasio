/**
 * Clase GestorPersonas encargada de administrar el conjunto de personas del sistema (socios, entrenadores, etc.).
 * Extiende de GestorGenerico<Persona> e implementa métodos de validación, búsqueda y filtrado:
 * - agregarPersona: Valida que no exista previamente una persona con el mismo DNI antes de registrarla.
 * - buscarPorId: Busca y retorna una persona según su identificador único.
 * - buscarPorDni: Busca y retorna una persona según su DNI (ignorando mayúsculas/minúsculas).
 * - obtenerSocios: Filtra y retorna una lista con todos los elementos que corresponden a socios.
 * - obtenerEntrenadores: Filtra y retorna una lista con todos los elementos que corresponden a entrenadores.
 */

package gestor;

import dao.PersonaDAO;
import excepciones.RegistroDuplicadoException;
import modelo.Entrenador;
import modelo.Persona;
import modelo.Socio;

import java.util.ArrayList;
import java.util.List;

public class GestorPersonas extends GestorGenerico<Persona> {

    private final PersonaDAO personaDAO = new PersonaDAO();

    public GestorPersonas() {
        this.elementos = personaDAO.listarTodos();
    }

    public void agregarPersona(Persona nueva) throws RegistroDuplicadoException {
        if (buscarPorDni(nueva.getDni()) != null) {
            throw new RegistroDuplicadoException("Ya existe una persona registrada con el DNI " + nueva.getDni() + ".");
        }
        personaDAO.insertar(nueva);
        super.agregar(nueva);
    }

    @Override
    public void actualizar(Persona persona) {
        personaDAO.actualizar(persona);
    }

    @Override
    public void eliminar(Persona elemento) {
        personaDAO.eliminar(elemento.getId());
        super.eliminar(elemento);
    }

    @Override
    public Persona buscarPorId(int id) {
        for (Persona p : elementos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Persona buscarPorDni(String dni) {
        for (Persona p : elementos) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                return p;
            }
        }
        return null;
    }

    public List<Socio> obtenerSocios() {
        List<Socio> socios = new ArrayList<>();
        for (Persona p : elementos) {
            if (p instanceof Socio) {
                socios.add((Socio) p);
            }
        }
        return socios;
    }

    public List<Entrenador> obtenerEntrenadores() {
        List<Entrenador> entrenadores = new ArrayList<>();
        for (Persona p : elementos) {
            if (p instanceof Entrenador) {
                entrenadores.add((Entrenador) p);
            }
        }
        return entrenadores;
    }

    public void agregarSoloEnMemoria(Persona p) {
        if (buscarPorDni(p.getDni()) == null) {
            super.agregar(p);
        }
    }
}
