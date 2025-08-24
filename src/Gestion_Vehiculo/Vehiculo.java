/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Vehiculo;
import java.time.LocalDate;
/**
 *
 * @author Joan
 */
public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int año;
    private TipoVehiculo tipo;
    private EstadoVehiculo estado;
    private static final int ANTIGUEDAD_MAXIMA = 20;
    
    public Vehiculo(String placa, String marca, String modelo, int año, TipoVehiculo tipo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.tipo = tipo;
        this.estado = EstadoVehiculo.DISPONIBLE;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getAño() {
        return año;
    }
    
    public TipoVehiculo getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }
    
    public EstadoVehiculo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }
    
    public boolean estaDisponible() {
        return estado == EstadoVehiculo.DISPONIBLE;
    }
    
    public boolean estaAlquilado() {
        return estado == EstadoVehiculo.EN_ALQUILER;
    }
    
    public static boolean validarAño(int año) {
        int añoActual = LocalDate.now().getYear();
        return año <= añoActual && año >= (añoActual - ANTIGUEDAD_MAXIMA);
    }
    
    public int calcularAntiguedad() {
        return LocalDate.now().getYear() - año;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s (%d) - Placa: %s - Estado: %s", 
                marca, modelo, tipo.getDescripcion(), año, placa, estado.getDescripcion());
    }   
}
