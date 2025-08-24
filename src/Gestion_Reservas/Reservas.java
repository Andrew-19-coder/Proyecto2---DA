/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Reservas;

import Gestion_Personas.Cliente;
import Gestion_Vehiculo.Vehiculo;
import java.time.LocalDate;

/**
 *
 * @author itsth
 */
public class Reservas {
    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicioAlquiler;
    private LocalDate fechaFinAlquiler;

    public Reservas(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicioAlquiler, LocalDate fechaFinAlquiler) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaInicioAlquiler = fechaInicioAlquiler;
        this.fechaFinAlquiler = fechaFinAlquiler;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDate getFechaInicioAlquiler() {
        return fechaInicioAlquiler;
    }

    public LocalDate getFechaFinAlquiler() {
        return fechaFinAlquiler;
    }
}
