/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Gestion_Vehiculo;

/**
 *
 * @author Joan
 */
public enum TipoVehiculo {
   SEDAN("Sedán"),
    SUV("SUV"),
    PICKUP("Pick-up");
    
    private String descripcion;
    
    private TipoVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }   
}
