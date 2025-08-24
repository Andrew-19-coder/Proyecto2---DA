/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Listas;

import Gestion_Reservas.Reservas;
import Gestion_Vehiculo.Vehiculo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author itsth
 */
public class ListaReservas implements List<Reservas> {
    Queue<Reservas> reservasEspera = new LinkedList<>();
    ArrayList<Reservas> reservasActivas = new ArrayList<>();
    ListaClientes clientes;
    ListaVehiculos vehiculos;

    public ListaReservas(ListaClientes clientes, ListaVehiculos vehiculos) {
        this.clientes = clientes;
        this.vehiculos = vehiculos;
    }
    
    @Override
    public boolean agregar(Reservas t) {
        if(clientes.buscar(t.getCliente().getCedula()) == null) return false;
        if(vehiculos.buscarPorPlaca(t.getVehiculo().getPlaca()) == null) return false;
        if(t.getFechaInicioAlquiler().isBefore(LocalDate.now())) return false;
        if(!t.getFechaFinAlquiler().isAfter(t.getFechaInicioAlquiler())) return false;
        if(t.getFechaInicioAlquiler().until(t.getFechaFinAlquiler()).getDays() > 30) return false;
        
        if (vehiculoDisponible(t.getVehiculo(), t.getFechaInicioAlquiler(), t.getFechaFinAlquiler())) {
            reservasActivas.add(t);
            return true;
        } else {
            reservasEspera.add(t);
            return false;
        }
    }
    
    @Override
    public boolean actualizar(Reservas t) {
        for(int i = 0; i < reservasActivas.size(); i++){
            if(reservasActivas.get(i).equals(t)){
                reservasActivas.set(i, t);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(Reservas t) {
        if(reservasActivas.remove(t)) return true;
        if(reservasEspera.remove(t)) return true;
        return false;
    }

    @Override
    public Reservas buscar(Object id) {
        String cedula = id.toString();
        for(Reservas r : reservasActivas){
            if(r.getCliente().getCedula().equals(cedula)) return r;
        }
        for(Reservas r : reservasEspera){
            if(r.getCliente().getCedula().equals(cedula)) return r;
        }
        return null;
    }
    
    private boolean vehiculoDisponible(Vehiculo v, LocalDate inicio, LocalDate fin) {
        for (Reservas t : reservasActivas) {
            if (t.getVehiculo().equals(v)) {
                boolean overlap = !(fin.isBefore(t.getFechaInicioAlquiler()) || inicio.isAfter(t.getFechaFinAlquiler()));
                if (overlap) return false;
            }
        }
        return true;
    }
    
    public void procesarReservasEspera(){
        Queue<Reservas> pendientes = new LinkedList<>();
        while(!reservasEspera.isEmpty()){
            Reservas r = reservasEspera.poll();
            if (vehiculoDisponible(r.getVehiculo(), r.getFechaInicioAlquiler(), r.getFechaFinAlquiler())) {
                reservasActivas.add(r);
            }else{
                pendientes.add(r);
            }
        }
        reservasEspera = pendientes;
    }
}
