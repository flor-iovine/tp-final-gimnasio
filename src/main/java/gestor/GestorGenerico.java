
/**
 * Clase abstracta genérica GestorGenerico<T> que provee la estructura base para la gestión de colecciones de elementos.
 * Define operaciones CRUD básicas y un método abstracto para la búsqueda por identificador:
 * - Constructor: Inicializa la lista interna de elementos como un ArrayList.
 * - agregar: Añade un nuevo elemento a la colección.
 * - eliminar: Remueve un elemento existente de la colección.
 * - obtenerTodos: Retorna la lista completa de elementos almacenados.
 * - buscarPorId: Método abstracto que debe ser implementado por las subclases para buscar un elemento por su ID.
 */

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
