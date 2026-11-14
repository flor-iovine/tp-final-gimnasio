package modelo;

import excepciones.MembresiaInactivaException;

import java.time.LocalDate;

public class PaseTresVeces extends Membresia{
    private static final int asistenciasSemanalesTotales = 3;
    private int asistenciasUsadas;

    public PaseTresVeces(int id, double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento, int asistenciasUsadas, int asistenciasSemanalesTotales) {
        super(id, precioBase, fechaInicio, fechaVencimiento);
        this.asistenciasUsadas = 0;
    }

    public int getAsistenciasSemanalesTotales() {
        return asistenciasSemanalesTotales;
    }

    public int getAsistenciasUsadas() {
        return asistenciasUsadas;
    }

    public void setAsistenciasUsadas(int asistenciasUsadas) {
        this.asistenciasUsadas = asistenciasUsadas;
    }


    @Override
    public String toString() {
        return "PaseTresVeces{" +
                "asistenciasSemanalesTotales=" + asistenciasSemanalesTotales +
                ", asistenciasUsadas=" + asistenciasUsadas +
                "} " + super.toString();
    }

    @Override
    public double calcularCosto() {
        return this.precioBase;
    }

    @Override
    public boolean permitirAcceso() throws MembresiaInactivaException {
        if (!estaActiva()) {
            throw new MembresiaInactivaException("Acceso denegado: La membresía está vencida.");
        }
        if (asistenciasUsadas < asistenciasSemanalesTotales) {
            asistenciasUsadas++;
            return true;
        }

        throw new MembresiaInactivaException("Acceso denegado: Ya alcanzó el límite de asistencias semanales.");
    }
    
}
