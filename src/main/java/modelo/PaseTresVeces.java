package modelo;

import excepciones.MembresiaInactivaException;

import java.time.LocalDate;

public class PaseTresVeces extends Membresia{
    private static final int LIMITE_SEMANAL = 3;
    private int asistenciasUsadas;

    public PaseTresVeces( double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        super(precioBase, fechaInicio, fechaVencimiento);
        this.asistenciasUsadas = 0;
    }

    public int getAsistenciasUsadas() {
        return asistenciasUsadas;
    }


    @Override
    public String toString() {
        return "PaseTresVeces{" +
                "asistenciasSemanalesTotales=" + LIMITE_SEMANAL +
                ", asistenciasUsadas=" + asistenciasUsadas +
                "} " + super.toString();
    }

    @Override
    public double calcularCosto() {
        return this.precioBase;
    }

    @Override
    public boolean estaActivo() {
        return super.estaActivo() && this.asistenciasUsadas < LIMITE_SEMANAL;
    }


    public void registrarAsistencia() throws MembresiaInactivaException {
        if (!estaActivo()) {
            throw new MembresiaInactivaException("Pase vencido o límite semanal de 3 asistencias alcanzado.");
        }
        this.asistenciasUsadas++;
    }
    
}
