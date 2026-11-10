package modelo;

import java.time.LocalDate;

public class PaseTresVeces extends Membresia{
    private int asistenciasSemanalesTotales ;
    private int asistenciasUsadas;

    public PaseTresVeces(int id, double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento, int asistenciasUsadas, int asistenciasSemanalesTotales) {
        super(id, precioBase, fechaInicio, fechaVencimiento);
        this.asistenciasUsadas = asistenciasUsadas;
        this.asistenciasSemanalesTotales = asistenciasSemanalesTotales;
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
        return 0;
    }

    @Override
    public boolean permitirAcceso() {
        return false;
    }
    
}
