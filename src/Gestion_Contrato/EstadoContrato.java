/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Gestion_Contrato;

/**
 *
 * @author Joan
 */
public enum EstadoContrato {
   ACTIVO("Activo"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");
    
    private String descripcion;
    
    private EstadoContrato(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }   
}
