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

import es.albarregas.Modelo.Productos;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ventura
 */
public class LineasPedidosDAO extends Conexion {

    public void addLineasPedido(ArrayList<Productos> seleccion, int idPedido) {

        String query = "insert into lineasPedidos values ( ?,?,?, 1,? )";
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            conexion.setAutoCommit(false);
            for (int i = 0; i < seleccion.size(); i++) {
                sentencia.setInt(1, idPedido);
                sentencia.setInt(2, i + 1);
                sentencia.setString(3, seleccion.get(i).getId());
                sentencia.setDouble(4, seleccion.get(i).getPrecio());
                sentencia.addBatch();
            }

            sentencia.executeBatch();
            conexion.commit();

            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }

    }

    public int getUltimaLinea(int idPedido) {
        int linea = 0;
        String query = "select max(numeroLinea)from lineaspedidos where numeroPedido = ?";
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1, idPedido);
            resultado = sentencia.executeQuery();
            resultado.absolute(1);
            linea = resultado.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return linea;
    }

    public void continuePedido(int idPedido, ArrayList<Productos> seleccion, int ultimaLinea) {
        
        
        String query = "insert into lineasPedidos values ( ?,?,?, 1,? )";
        try {
            iniciarConexion();
            
            sentencia = conexion.prepareStatement(query);
            conexion.setAutoCommit(false);
            
            for (int i=0; i < seleccion.size(); i++) {
                sentencia.setInt(1, idPedido);
                sentencia.setInt(2, ultimaLinea+i + 1);
                sentencia.setString(3, seleccion.get(i).getId());
                sentencia.setDouble(4, seleccion.get(i).getPrecio());
                sentencia.addBatch();
            }

            sentencia.executeBatch();
            conexion.commit();

            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

}
