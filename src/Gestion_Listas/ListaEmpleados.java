/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Listas;
import java.util.ArrayList;
import Gestion_Personas.Empleado;
import Gestion_Personas.Persona;

/**
 *
 * @author Jeshuan
 */
public class ListaEmpleados implements List<Empleado>{
   private ArrayList<Empleado> empleados;
    
    public ListaEmpleados() {
        this.empleados = new ArrayList<>();
    }
    
    public int getCantidad() {
        return empleados.size();
    }

    @Override
    public boolean agregar(Empleado t) {
        if(t == null) return false;
        if(buscar(t.getCedula()) != null) return false;
        if(!Persona.esMayorEdad(t.getFechaNacimiento())) return false;
        if(!Persona.validarCorreo(t.getCorreo())) return false;
        if(!Persona.validarTelefono(t.getTelefono())) return false;
        
        empleados.add(t);
        return true;
    }

    @Override
    public boolean actualizar(Empleado t) {
        if(t == null) return false;
        
        Empleado existente = buscar(t.getCedula());
        if(existente == null) return false;
        
        existente.setTelefono(t.getTelefono());
        existente.setCorreo(t.getCorreo());
        if(t.getPuesto() != null){
            existente.setPuesto(t.getPuesto());
        }
        return true;
    }

    @Override
    public boolean eliminar(Empleado t) {
        return empleados.remove(t);
    }

    @Override
    public Empleado buscar(Object id) {
        if(id == null) return null;
        String cedula = String.valueOf(id);
        for(Empleado e : empleados){
            if(e.getCedula().equals(cedula)) return e;
        }
        return null;
    }
     public ArrayList<Empleado> getTodos() {
        return new ArrayList<>(empleados);
    }
    }
