package gestor;

import modelo.SesionGuiada;

import java.util.List;

public class GestorSesiones extends GestorGenerico<SesionGuiada> {

    @Override
    void agregar(SesionGuiada elemento) {

    }

    @Override
    void eliminar(SesionGuiada elemento) {

    }

    @Override
    List<SesionGuiada> obtenerTodos() {
        return List.of();
    }

    @Override
    SesionGuiada buscarPorId(int id) {
        return null;
    }
}
