package gestor;

import dao.MembresiaDAO;
import modelo.Socio;
import modelo.Membresia;
import java.util.ArrayList;
import java.util.List;

public class GestorMembresias extends GestorGenerico<Membresia> {

    private final MembresiaDAO membresiaDAO = new MembresiaDAO();

    public void cargarDesdeDB(List<Socio> socios) {
        this.elementos = membresiaDAO.listarYAsociarSocios(socios);
    }

    public void agregarParaSocio(Membresia m, Socio socio) {
        membresiaDAO.insertarParaSocio(m, socio.getId());
        super.agregar(m);
    }

    @Override
    public void actualizar(Membresia m) {
        membresiaDAO.actualizar(m);
    }

    @Override
    public void eliminar(Membresia elemento) {
        membresiaDAO.eliminar(elemento.getId());
        super.eliminar(elemento);
    }

    @Override
    public Membresia buscarPorId(int id) {
        for (Membresia m : elementos) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public List<Membresia> obtenerVencidas() {
        List<Membresia> vencidas = new ArrayList<>();
        for (Membresia m : elementos) {
            if (!m.estaActivo()) {
                vencidas.add(m);
            }
        }
        return vencidas;
    }

    public void agregarSoloEnMemoria(Membresia m) {
        super.agregar(m);
    }


}
