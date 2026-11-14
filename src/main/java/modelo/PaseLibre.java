package modelo;

import excepciones.MembresiaInactivaException;
import interfaces.Promocionable;

import java.time.LocalDate;

public class PaseLibre extends Membresia implements Promocionable {


    public PaseLibre(int id, double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        super(id, precioBase, fechaInicio, fechaVencimiento);
    }

    @Override
    public double calcularCosto() {
        return aplicarDescuento(this.precioBase);
    }

    @Override
    public boolean permitirAcceso() throws MembresiaInactivaException {
        if (!estaVigente()) {
            throw new MembresiaInactivaException("Acceso denegado: La membresía está vencida.");
        }
        return true;
    }

    @Override
    public double aplicarDescuento(double precioBase) {
        return precioBase * 0.90; //10 por ciento de descuento
    }
}
