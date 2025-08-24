/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Listas;

import Gestion_Vehiculo.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author Joan
 */
public class ListaVehiculos implements List<Vehiculo> {
 private HashMap<String, Vehiculo> vehiculos;
    
    public ListaVehiculos() {
        this.vehiculos = new HashMap<>();
    }

    @Override
    public boolean agregar(Vehiculo t) {
        if (t == null || vehiculos.containsKey(t.getPlaca())) return false;
        if(!Vehiculo.validarAño(t.getAño())) return false;

        vehiculos.put(t.getPlaca(), t);
        return true;
    }

    @Override
    public boolean actualizar(Vehiculo t) {
        if (t == null) return false;

        Vehiculo existente = vehiculos.get(t.getPlaca());
        if (existente == null) return false;

        existente.setModelo(t.getModelo());
        existente.setTipo(t.getTipo());
        existente.setEstado(t.getEstado());
        return true;
    }

    @Override
    public boolean eliminar(Vehiculo t) {
        if (t == null) return false;

        Vehiculo existente = vehiculos.get(t.getPlaca());
        if (existente == null) return false;

        if (existente.estaAlquilado()) {
            throw new IllegalStateException("No se puede eliminar un vehículo en alquiler.");
        }

        vehiculos.remove(t.getPlaca());
        return true;
    }

    @Override
    public Vehiculo buscar(Object id) {
        if (id == null || !(id instanceof String)) return null;
        String placa = (String) id;
        return vehiculos.get(placa);
    }
    
    public Vehiculo buscarPorTipo(String tipo){
        for(Vehiculo v : vehiculos.values()){
            if(v.getTipo().equals(tipo)){
                return v;
            }
        }
        return null;
    }
    
    public Vehiculo buscarPorPlaca(String placa){
        return vehiculos.get(placa);
    }
    
    public int getCantidad() {
        return vehiculos.size();
    }
    public ArrayList<Vehiculo> obtenerTodos() {
    ArrayList<Vehiculo> lista = new ArrayList<>();
    for (Vehiculo v : vehiculos.values()) {
        lista.add(v);
    }
    return lista;
}    
}
