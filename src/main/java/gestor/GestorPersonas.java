package gestor;

import modelo.Persona;

import java.util.List;

public class GestorPersonas extends GestorGenerico<Persona> {

    @Override
    void agregar(Persona elemento) {

    }

    @Override
    void eliminar(Persona elemento) {

    }

    @Override
    List<Persona> obtenerTodos() {
        return List.of();
    }

    @Override
    Persona buscarPorId(int id) {
        return null;
    }
}
