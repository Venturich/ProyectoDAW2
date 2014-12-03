/*
 *  
 *  	Proyecto para:
 *  	DI DWC DWE
 *  
 *  	Autor:
 * 	Ventura Preciado S�nchez
 * 
 * 	DAW2 IES ALBARREGAS
 * 
 */
package es.albarregas.Modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ventura
 */
public class Pedidos  implements Serializable{
    private int idPedido;
    private Date fecha;
    private int idCliente;
    private String estado;
    private double baseImporte;
    private double iva;

    public Pedidos() {
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getBaseImporte() {
        return baseImporte;
    }

    public void setBaseImporte(double baseImporte) {
        this.baseImporte = baseImporte;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
    
    
    
}
