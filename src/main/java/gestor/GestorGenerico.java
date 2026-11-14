package gestor;

import java.util.List;

public abstract class GestorGenerico<T> {

    List<T> elementos;

    abstract void agregar(T elemento);

    abstract void eliminar(T elemento);

    abstract List<T> obtenerTodos();

    abstract T buscarPorId(int id);

}

