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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ventura
 */
public class LineasPedidosDAO extends Conexion{

    public void addLineasPedido(ArrayList<Productos> seleccion, int idPedido) {

        String query= "insert into lineasPedidos values ( ?,?,?, 1,? )";        
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            conexion.setAutoCommit(false);
            for(int i=0;i<seleccion.size(); i++){
                sentencia.setInt(1, idPedido);
                sentencia.setInt(2,i+1);
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
