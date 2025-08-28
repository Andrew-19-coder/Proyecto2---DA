/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Joan
 */
public class ReservaActivaException extends Exception {
    public ReservaActivaException() {
        super("No se puede eliminar el cliente: tiene reservas activas");
    }

    public ReservaActivaException(String nombreCliente) {
        super("No se puede eliminar el cliente " + nombreCliente + ": tiene reservas activas");
     }  
}
