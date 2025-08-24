/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Personas;
import java.time.LocalDate;
import java.time.Period;
/**
 *
 * @author Jeshuan
 */
public class Cliente extends Persona{
    private String licenciaConducir;
    private LocalDate fechaExpedicion;
    
    public Cliente(String cedula, String nombre, LocalDate fechaNacimiento, String telefono, String correo, String licenciaConducir, LocalDate fechaExpedicion) {
        super(cedula, nombre, fechaNacimiento, telefono, correo);
        this.licenciaConducir = licenciaConducir;
        this.fechaExpedicion = fechaExpedicion;
        }
    

    public String getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }
    
    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }
    
    public  boolean licenciaVigente(){
        LocalDate hoy = LocalDate.now();
        if(this.fechaExpedicion.isAfter(hoy)){
            return false;
        }
        return fechaExpedicion.plusYears(6).isAfter(hoy);
       
    }
    public boolean tieneLicencia(){
        if(licenciaConducir==null){
            return false;
        }
        if(!licenciaConducir.equals(cedula)){
            return false;
        }
        LocalDate fechaMayorEdad = this.fechaNacimiento.plusYears(18);
        if(this.fechaExpedicion.isBefore(fechaMayorEdad)){
           return false;
        }
        if(!licenciaVigente()){
            return false;
        }
        
        return true;
    } 

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }
    
}
