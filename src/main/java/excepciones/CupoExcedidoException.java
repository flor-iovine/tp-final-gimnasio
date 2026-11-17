/**
 * Excepción personalizada CupoExcedidoException que se lanza cuando
 * se intenta inscribir a un participante en una sesión que ya ha alcanzado su límite máximo de capacidad.
 * Extiende de Exception para indicar que es una excepción chequeada.
 */

package excepciones;

public class CupoExcedidoException extends Exception {
    public CupoExcedidoException(String message) {
        super(message);
    }
}
