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

    public int setNuevoPedido(int idCliente) {
        int idPedido = -1;
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement("insert into pedidos values(null, null, ?, 'n',null,null)", sentencia.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, idCliente);
            sentencia.executeUpdate();
            resultado = sentencia.getGeneratedKeys();
            while (resultado.next()) {
                idPedido = resultado.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return idPedido;
    }

    public void finalizarPedido(int idPedido, double total, String estado) {
        String query = "update pedidos set fecha=CURRENT_TIMESTAMP, estado= ?, baseImporte=?, iva=21.00 where idPedido=?";

        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, estado);
            sentencia.setDouble(2, total);
            sentencia.setInt(3, idPedido);
            sentencia.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

}
