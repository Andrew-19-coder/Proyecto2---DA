/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Gestion_Vehiculo;

/**
 *
 * @author Joan
 */
public enum EstadoVehiculo {
  DISPONIBLE("Disponible"),
    EN_ALQUILER("En alquiler"),
    EN_MANTENIMIENTO("En mantenimiento");
    
    private String descripcion;
    
    private EstadoVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }  
}
