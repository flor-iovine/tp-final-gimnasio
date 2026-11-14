package modelo;

import excepciones.MembresiaInactivaException;

import java.time.LocalDate;

public class PaseTresVeces extends Membresia{
    private int asistenciasSemanalesTotales ;
    private int asistenciasUsadas;

    public PaseTresVeces(int id, double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento, int asistenciasUsadas, int asistenciasSemanalesTotales) {
        super(id, precioBase, fechaInicio, fechaVencimiento);
        this.asistenciasUsadas = 0;
        this.asistenciasSemanalesTotales = 3;
    }

    public int getAsistenciasSemanalesTotales() {
        return asistenciasSemanalesTotales;
    }

    public void setAsistenciasSemanalesTotales(int asistenciasSemanalesTotales) {
        this.asistenciasSemanalesTotales = asistenciasSemanalesTotales;
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
        if (!estaVigente()) {
            throw new MembresiaInactivaException("Acceso denegado: La membresía está vencida.");
        }
        if (asistenciasUsadas < asistenciasSemanalesTotales) {
            asistenciasUsadas++;
            return true;
        }

        throw new MembresiaInactivaException("Acceso denegado: Ya alcanzó el límite de asistencias semanales.");
    }
    
}
