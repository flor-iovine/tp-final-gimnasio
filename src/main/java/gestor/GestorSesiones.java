package gestor;

import dao.SesionGuiadaDAO;
import modelo.Entrenador;
import modelo.SesionGuiada;
import java.util.ArrayList;
import java.util.List;

public class GestorSesiones extends GestorGenerico<SesionGuiada> {

    private final SesionGuiadaDAO sesionDAO = new SesionGuiadaDAO();

    public void cargarDesdeDB(List<Entrenador> entrenadores) {
        this.elementos = sesionDAO.listarConEntrenadores(entrenadores);
    }

    @Override
    public void agregar(SesionGuiada s) {
        sesionDAO.insertar(s);
        super.agregar(s);
    }

    @Override
    public void actualizar(SesionGuiada s) {
        sesionDAO.actualizar(s);
    }

    @Override
    public void eliminar(SesionGuiada s) {
        sesionDAO.eliminar(s.getIdSesion());
        super.eliminar(s);
    }

    @Override
    public SesionGuiada buscarPorId(int id) {
        for (SesionGuiada s : elementos) {
            if (s.getIdSesion() == id) {
                return s;
            }
        }
        return null;
    }

    public List<SesionGuiada> obtenerSesionesDisponibles() {
        List<SesionGuiada> disponibles = new ArrayList<>();
        for (SesionGuiada s : elementos) {
            if (s.cupoDisponible()) {
                disponibles.add(s);
            }
        }
        return disponibles;
    }

    public void agregarSoloEnMemoria(SesionGuiada s) {
        super.agregar(s);
    }
}
