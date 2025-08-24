/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Personas;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

/**
 *
 * @author itsth
 */
public class Persona {
    protected String cedula;
    protected String nombre;
    protected LocalDate fechaNacimiento;
    private static final String phoneRegex = "^[0-9]{4}-[0-9]{4}$";
    protected String telefono;
    private static final String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$";
    protected String correo;

    public Persona(String cedula, String nombre, LocalDate fechaNacimiento, String telefono, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }
    
    public static boolean esMayorEdad(LocalDate fechaNacimiento){
        LocalDate hoy = LocalDate.now();
        int edad = Period.between(fechaNacimiento, hoy).getYears();
        return edad >= 18;
    }
    
       public void setCorreo(String correo) {
        if(validarCorreo(correo)){
            this.correo = correo;
    }
      }
      public void setTelefono(String telefono) {
        if(validarTelefono(telefono)){
            this.telefono = telefono;
        }
      }
    public static boolean validarTelefono(String telefono){
        return Pattern.matches(phoneRegex, telefono);
    }
    public static boolean validarCorreo(String correo){
        return Pattern.matches(emailRegex, correo);
    }  
}
