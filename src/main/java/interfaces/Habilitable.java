/**
 * Interfaz Habilitable que define el contrato para aquellos elementos o entidades
 * que poseen un estado de actividad dentro del sistema.
 * Define el siguiente metodo:
 * - estaActivo: Retorna un valor booleano indicando si el elemento se encuentra activo (true) o inactivo (false).
 */
package interfaces;

public interface Habilitable {
    boolean estaActivo();
}