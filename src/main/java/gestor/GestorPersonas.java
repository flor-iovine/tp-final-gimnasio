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
}
