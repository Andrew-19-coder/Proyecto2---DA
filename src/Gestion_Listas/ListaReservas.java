/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Listas;

import Gestion_Personas.Cliente;
import Gestion_Reservas.Reservas;
import Gestion_Vehiculo.TipoVehiculo;
import Gestion_Vehiculo.Vehiculo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author itsth
 */
public class ListaReservas implements List<Reservas>{
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
        if(vehiculos.buscar(t.getVehiculo().getPlaca()) == null) return false;
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
    
    public boolean tieneReservasActivas(Cliente cliente) {
    for (Reservas r : reservasActivas) {
    if (r.getCliente().equals(cliente)) {
            return true; 
            }
        }
        return false; 
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
    
    public ArrayList<Reservas> getReservasActivas() {
        return new ArrayList<>(reservasActivas);
    }
    
    public ArrayList<Reservas> getReservasEspera() {
        return new ArrayList<>(reservasEspera);
    }
    private Vehiculo buscarVehiculoDisponiblePorTipo(TipoVehiculo tipo, LocalDate inicio, LocalDate fin) {
    for (Vehiculo v : vehiculos.obtenerTodos()) {
        if (v.getTipo() == tipo && v.estaDisponible()) {
            if (vehiculoDisponibleEnFechas(v, inicio, fin)) {
                return v;
                }
            }
        }
        return null;
    }

    private boolean vehiculoDisponibleEnFechas(Vehiculo v, LocalDate inicio, LocalDate fin) {
    for (Reservas r : reservasActivas) {
        if (r.getVehiculo().getPlaca().equals(v.getPlaca())) {
            if (inicio.isBefore(r.getFechaFinAlquiler()) && fin.isAfter(r.getFechaInicioAlquiler())) {
                return false;
            }
        }
    }
    return true;
}


    public boolean crearReservaPorTipo(Cliente cliente, TipoVehiculo tipoDeseado, LocalDate inicio, LocalDate fin) {
    if(clientes.buscar(cliente.getCedula()) == null) return false;
    if(inicio.isBefore(LocalDate.now())) return false;
    if(!fin.isAfter(inicio)) return false;
    if(inicio.until(fin).getDays() > 30) return false;
    
    Vehiculo vehiculoAsignado = buscarVehiculoDisponiblePorTipo(tipoDeseado, inicio, fin);
    
    if (vehiculoAsignado != null) {
        Reservas nuevaReserva = new Reservas(cliente, vehiculoAsignado, inicio, fin);
        reservasActivas.add(nuevaReserva);
        return true;
    } else {
        Reservas reservaEspera = new Reservas(cliente, null, inicio, fin);
        reservasEspera.add(reservaEspera);
        return false;
    }
}


    public ArrayList<Reservas> buscarPorCliente(String cedula) {
    ArrayList<Reservas> resultado = new ArrayList<>();
    
    for (Reservas r : reservasActivas) {
        if (r.getCliente().getCedula().equals(cedula)) {
            resultado.add(r);
        }
    }
    
    for (Reservas r : reservasEspera) {
        if (r.getCliente().getCedula().equals(cedula)) {
            resultado.add(r);
        }
    }
    
    return resultado;
}

    public ArrayList<Reservas> buscarPorFechas(LocalDate inicio, LocalDate fin) {
    ArrayList<Reservas> resultado = new ArrayList<>();
    
    for (Reservas r : reservasActivas) {
        if (!(fin.isBefore(r.getFechaInicioAlquiler()) || inicio.isAfter(r.getFechaFinAlquiler()))) {
            resultado.add(r);
        }
    }
    
    return resultado;
}


    public boolean modificarVehiculo(Reservas reserva, TipoVehiculo nuevoTipo) {
    Vehiculo nuevoVehiculo = buscarVehiculoDisponiblePorTipo(nuevoTipo, reserva.getFechaInicioAlquiler(), reserva.getFechaFinAlquiler());
    
    if (nuevoVehiculo != null) {
        Reservas reservaModificada = new Reservas(reserva.getCliente(), nuevoVehiculo, reserva.getFechaInicioAlquiler(), reserva.getFechaFinAlquiler());
        reservasActivas.remove(reserva);
        reservasActivas.add(reservaModificada);
        return true;
    }
    return false;
    }
}