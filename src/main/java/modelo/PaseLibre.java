package modelo;

import java.time.LocalDate;

public class PaseLibre extends Membresia{


    public PaseLibre(int id, double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        super(id, precioBase, fechaInicio, fechaVencimiento);
    }

    @Override
    public double calcularCosto() {
        return this.precioBase;
    }

    @Override
    public boolean permitirAcceso() {
        return false;
    }

}
