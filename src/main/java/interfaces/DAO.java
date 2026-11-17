package interfaces;

import java.util.List;

public interface DAO<T> {

    void insertar(T entidad);

    List<T> listarTodos();

    void actualizar(T entidad);

    void eliminar(int id);
}