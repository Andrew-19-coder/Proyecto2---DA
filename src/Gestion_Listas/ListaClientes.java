/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Listas;

import Gestion_Personas.Cliente;
import Gestion_Personas.Persona;
import java.util.ArrayList;

/**
 *
 * @author Jeshuan
 */
public class ListaClientes implements List<Cliente> {
  private ArrayList<Cliente> clientes;
  
   public ListaClientes() {
        this.clientes = new ArrayList<>();
    }
    
    public ArrayList<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }
    
    public int getCantidad() {
        return clientes.size();
    }
    @Override
    public boolean agregar(Cliente t) {
     if(Persona.esMayorEdad(t.getFechaNacimiento()) && t.tieneLicencia() && buscar(t.getCedula()) == null){
            return clientes.add(t);
        }
        return false;

    }

    @Override
    public boolean actualizar(Cliente t) {
      for(int i = 0; i < clientes.size(); i++){
            if(clientes.get(i).getCedula().equals(t.getCedula())){
                clientes.set(i, t);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(Cliente t) {
    return clientes.remove(t);
    }

    @Override
    public Cliente buscar(Object id) {
    for(Cliente c : clientes){
            if(c.getCedula().equals(id.toString())){
                return c;
            }
        }
        return null;
    }
    }
