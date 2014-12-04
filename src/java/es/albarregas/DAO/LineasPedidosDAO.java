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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ventura
 */
public class LineasPedidosDAO extends Conexion{

    public void addLineasPedido(String[]codigos, int idPedido) {

        String query= "insert into lineas lineasPedidos values ( ?,?,?,1,? )";
        try {
            iniciarConexion();
            sentencia= conexion.prepareStatement("select idProducto, precio from productos where idProducto in (?) ");
            for(int i=1; i<=codigos.length;i++){
                sentencia.setInt(1, idPedido);
                
            }
            resultado=sentencia.executeQuery();
            sentencia = conexion.prepareStatement(query);
            int linea=1;
            while (resultado.next()){
                
            sentencia.setInt(1,idPedido);
            sentencia.setInt(2,linea);
            sentencia.setString(3,resultado.getString("idProducto"));
            sentencia.setDouble(4,resultado.getDouble("precio"));
            
            linea++;
            sentencia.addBatch();
            }
            sentencia.executeBatch();
            
            
            
            
            

            
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        
    }
    
}
