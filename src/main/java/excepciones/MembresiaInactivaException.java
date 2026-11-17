
/**
 * Excepción personalizada MembresiaInactivaException que se lanza cuando
 * se intenta realizar una operación que requiere de una membresía activa pero esta se encuentra inactiva.
 * Extiende de Exception para indicar que es una excepción chequeada.
 */
package excepciones;

public class MembresiaInactivaException extends Exception {
    public MembresiaInactivaException(String message) {
        super(message);
    }
}
