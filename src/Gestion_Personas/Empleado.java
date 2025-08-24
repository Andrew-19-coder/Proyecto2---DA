/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Personas;
import java.time.LocalDate;
/**
 *
 * @author Jeshuan
 */
public class Empleado extends Persona {
    Puesto_Empleados puesto;
    private int salario;
    
    public Empleado(String cedula, String nombre, LocalDate fechaNacimiento, String telefono, String correo, Puesto_Empleados puesto, int salario) {
        super(cedula, nombre, fechaNacimiento, telefono, correo);
        this.puesto = puesto;
        this.salario = salario;
    }
    
    public Puesto_Empleados getPuesto() {
        return puesto;
    }
    
    public void setPuesto(Puesto_Empleados puesto) {
        this.puesto = puesto;
    } 

    public int getSalario() {
        return salario;
    }
    
}
