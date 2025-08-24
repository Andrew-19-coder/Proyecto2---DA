/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Listas;
import Gestion_Contrato.*;
import Gestion_Personas.Cliente;
import Gestion_Vehiculo.EstadoVehiculo;
import Gestion_Vehiculo.Vehiculo;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Joan
 */
public class ListaContratos implements List<ContratoAlquiler>{
   private ArrayList<ContratoAlquiler> contratos;
    
    public ListaContratos() {
        this.contratos = new ArrayList<>();
    }
    
    @Override
    public boolean agregar(ContratoAlquiler t) {
        if (t == null) return false;
        
        if (t.getFechaInicio().isBefore(LocalDate.now())) return false;
        if (!t.getFechaFin().isAfter(t.getFechaInicio())) return false;
        
        for (ContratoAlquiler c : contratos) {
            if (c.getVehiculo().equals(t.getVehiculo()) && c.esActivo()) {
                boolean overlap = !(t.getFechaFin().isBefore(c.getFechaInicio()) || 
                                  t.getFechaInicio().isAfter(c.getFechaFin()));
                if (overlap) return false;
            }
        }
        
        t.getVehiculo().setEstado(EstadoVehiculo.EN_ALQUILER);
        contratos.add(t);
        return true;
    }
    
    @Override
    public boolean actualizar(ContratoAlquiler t) {
        return false;
    }
    
    @Override
    public boolean eliminar(ContratoAlquiler t) {
        if (t == null) return false;
        if (t.getEstado() == EstadoContrato.ACTIVO) {
            t.cancelarContrato();
        }
        return contratos.remove(t);
    }
    
    @Override
    public ContratoAlquiler buscar(Object id) {
        if (id == null) return null;
        
        if (id instanceof Integer) {
            int numeroContrato = (Integer) id;
            for (ContratoAlquiler c : contratos) {
                if (c.getNumeroContrato() == numeroContrato) return c;
            }
        } else if (id instanceof String) {
            String cedula = (String) id;
            for (ContratoAlquiler c : contratos) {
                if (c.getCliente().getCedula().equals(cedula) && c.esActivo()) {
                    return c;
                }
            }
        }
        return null;
    }
    
    public ArrayList<ContratoAlquiler> buscarPorCliente(String cedula) {
        ArrayList<ContratoAlquiler> resultado = new ArrayList<>();
        for (ContratoAlquiler c : contratos) {
            if (c.getCliente().getCedula().equals(cedula)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    
    public ArrayList<ContratoAlquiler> buscarPorVehiculo(String placa) {
        ArrayList<ContratoAlquiler> resultado = new ArrayList<>();
        for (ContratoAlquiler c : contratos) {
            if (c.getVehiculo().getPlaca().equals(placa)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    
    public void finalizarContrato(int numeroContrato) {
        ContratoAlquiler contrato = buscar(numeroContrato);
        if (contrato != null && contrato.esActivo()) {
            contrato.finalizarContrato();
        }
    }
    
    public void cancelarContrato(int numeroContrato) {
        ContratoAlquiler contrato = buscar(numeroContrato);
        if (contrato != null && contrato.getEstado() != EstadoContrato.FINALIZADO) {
            contrato.cancelarContrato();
        }
    }
    
    public ArrayList<ContratoAlquiler> obtenerActivos() {
        ArrayList<ContratoAlquiler> activos = new ArrayList<>();
        for (ContratoAlquiler c : contratos) {
            if (c.esActivo()) {
                activos.add(c);
            }
        }
        return activos;
    }
    
    public int getCantidad() {
        return contratos.size();
    }   
}
