/**
 * Interfaz genérica DAO<T> (Data Access Object) que define el contrato estándar
 * para las operaciones de persistencia y acceso a datos de cualquier entidad del sistema.
 * Define los siguientes métodos principales:
 * - insertar: Almacena una nueva entidad en la fuente de datos.
 * - listarTodos: Retorna una lista con todas las entidades almacenadas.
 * - actualizar: Modifica los datos de una entidad existente.
 * - eliminar: Remueve una entidad de la fuente de datos según su identificador único.
 */
package interfaces;

import java.util.List;

public interface DAO<T> {

    void insertar(T entidad);

    List<T> listarTodos();

    void actualizar(T entidad);

    void eliminar(int id);
}