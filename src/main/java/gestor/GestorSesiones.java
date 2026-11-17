package gestor;

import modelo.SesionGuiada;
import java.util.ArrayList;
import java.util.List;

public class GestorSesiones extends GestorGenerico<SesionGuiada> {

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
}
