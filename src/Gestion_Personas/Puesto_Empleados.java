/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Gestion_Personas;

/**
 *
 * @author Jeshuan
 */
public enum Puesto_Empleados {
   ADMINISTRADOR("Administrador"),
    ATENCION_CLIENTE("Atencion_cliente"),
    MECANICO("Mecanico"),
    GERENTE("Gerente"),
    CONTADOR("Contador");
    private String puesto;

    private Puesto_Empleados(String puesto) {
        this.puesto = puesto;
    } 
}
