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
package es.albarregas.DAO;

import es.albarregas.Modelo.Pedidos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ventura
 */
public class PedidosDAO extends Conexion {

    public ArrayList<Pedidos> getPedidosFinalizados(int id) {
        ArrayList<Pedidos> lista = new ArrayList<Pedidos>();
        String query = "select * from pedidos where (estado = 'p' OR estado = 'x') AND idCliente = ?";
        System.out.println(id+" !!!!!!!!!!!!!!!!!" );
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1, id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Pedidos ped = new Pedidos();
                ped.setBaseImporte(resultado.getDouble("baseImporte"));
                ped.setEstado(resultado.getString("estado"));
                ped.setFecha(resultado.getDate("fecha"));
                ped.setIdCliente(resultado.getInt("idCliente"));
                ped.setIdPedido(resultado.getInt("idPedido"));
                ped.setIva(resultado.getDouble("iva"));
                lista.add(ped);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return lista;
    }

    public ArrayList<Pedidos> getPedidosPendientes(int id) {
        ArrayList<Pedidos> lista = new ArrayList<Pedidos>();
        System.out.println(id+" !!!!!!!!!!!!!!!!!" );
        String query = "select * from pedidos where estado = 'n' AND idCliente = ?";
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1, id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Pedidos ped = new Pedidos();
                ped.setBaseImporte(resultado.getDouble("baseImporte"));
                ped.setEstado(resultado.getString("estado"));
                ped.setFecha(resultado.getDate("fecha"));
                ped.setIdCliente(resultado.getInt("idCliente"));
                ped.setIdPedido(resultado.getInt("idPedido"));
                ped.setIva(resultado.getDouble("iva"));
                lista.add(ped);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return lista;
    }

}
