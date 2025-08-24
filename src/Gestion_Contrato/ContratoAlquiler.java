/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Contrato;

import Gestion_Personas.Cliente;
import Gestion_Vehiculo.Vehiculo;
import Gestion_Reservas.Reservas;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import Gestion_Vehiculo.EstadoVehiculo;
/**
 *
 * @author Joan
 */
public class ContratoAlquiler {
  private static int contadorContratos = 1;
    private int numeroContrato;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double montoTotal;
    private EstadoContrato estado;
    private Reservas reservaOrigen;
    private static final double TARIFA_DIARIA = 50000;
    
    public ContratoAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.numeroContrato = contadorContratos++;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoContrato.ACTIVO;
        this.reservaOrigen = null;
        calcularMonto();
    }
    
    public ContratoAlquiler(Reservas reserva) {
        this.numeroContrato = contadorContratos++;
        this.cliente = reserva.getCliente();
        this.vehiculo = reserva.getVehiculo();
        this.fechaInicio = reserva.getFechaInicioAlquiler();
        this.fechaFin = reserva.getFechaFinAlquiler();
        this.estado = EstadoContrato.ACTIVO;
        this.reservaOrigen = reserva;
        calcularMonto();
    }
    
    private void calcularMonto() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        this.montoTotal = dias * TARIFA_DIARIA;
    }
    
    public void finalizarContrato() {
        if (estado == EstadoContrato.ACTIVO) {
            estado = EstadoContrato.FINALIZADO;
            vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        }
    }
    
    public void cancelarContrato() {
        if (estado != EstadoContrato.FINALIZADO) {
            estado = EstadoContrato.CANCELADO;
            if (vehiculo.getEstado() == EstadoVehiculo.EN_ALQUILER) {
                vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
            }
        }
    }
    
    public int getNumeroContrato() {
        return numeroContrato;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public double getMontoTotal() {
        return montoTotal;
    }
    
    public EstadoContrato getEstado() {
        return estado;
    }
    
    public boolean esActivo() {
        return estado == EstadoContrato.ACTIVO;
    }   
}
