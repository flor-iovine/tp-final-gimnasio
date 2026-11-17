/**
 * Excepción personalizada RegistroDuplicadoException que se lanza cuando
 * se intenta registrar un elemento que ya existe previamente en el sistema.
 * Extiende de Exception para indicar que es una excepción chequeada.
 */

package excepciones;

public class RegistroDuplicadoException extends Exception {
    public RegistroDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
