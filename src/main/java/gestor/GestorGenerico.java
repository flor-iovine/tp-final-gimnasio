package gestor;

import java.util.ArrayList;
import java.util.List;

public abstract class GestorGenerico<T> {
    protected List<T> elementos;

    public GestorGenerico() {
        this.elementos = new ArrayList<>();
    }

    public void agregar(T elemento) {
        this.elementos.add(elemento);
    }

    public void eliminar(T elemento) {
        this.elementos.remove(elemento);
    }

    public List<T> obtenerTodos() {
        return this.elementos;
    }

    public abstract T buscarPorId(int id);

    public abstract void actualizar(T elemento);
}
