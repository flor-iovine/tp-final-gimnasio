/**
 * Excepción personalizada SocioInscriptoPreviamenteException que se lanza cuando
 * se intenta registrar a un socio que ya se encuentra previamente inscripto.
 * Extiende de Exception para indicar que es una excepción chequeada.
 */

package excepciones;

public class SocioInscriptoPreviamenteException extends Exception {
    public SocioInscriptoPreviamenteException(String message) {
        super(message);
    }
}
